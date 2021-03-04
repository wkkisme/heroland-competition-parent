package com.heroland.competition.service.impl.task;

import com.anycommon.cache.service.RedisService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HeroLandTopicGroupService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 统计
 *
 * @author wangk
 */
@Component
@Slf4j
public class StatisticsTask {
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;
    /**
     * 统计所有人的比赛记录
     * 1 总表统计所有人的总记录，
     * 1 总得分
     * 2 总平均分
     * 3 完成率
     * 4 答对率
     * 5 胜率
     * 6 总时长
     * 2 详细表根据科目来统计所有人的记录
     * 1 总得分
     * *      2 总平均分
     * *      3 完成率
     * *      4 答对率
     * *      5 胜率
     * *      6 总时长
     * 7 总场数
     * 0 15 10 * * ? *
     * 0 0/10 * * * ?
     * 0/5 * * * *？
     *
     */
    @Scheduled(fixedDelay = 2000)
    @Transactional(rollbackFor = Exception.class)
    public void statistics() {
        try {
            log.info("start  statistics =================");
            if (!redisService.setNx("statistics_redis_key", true, "PT1h")) {
                log.info("start statistics is lock");
                return;
            }
            // 1 先清除历史版本
            HeroLandStatisticsTotalQO qo = new HeroLandStatisticsTotalQO();
            heroLandCompetitionStatisticsService.deleteHistoryStatisticsTotalAndDetailByQO(qo);

            for (CompetitionEnum value : CompetitionEnum.values()) {
                Integer type = value.getType();
                HeroLandStatisticsAllQO totalQo = new HeroLandStatisticsAllQO();
                totalQo.setHistory(false);

                /*
                 * 同步作业赛总分数 、总平均分、 总场次 total------>
                 * 需要group 机构。--》年级----》  班级----》 科目--》用户
                 */
                totalQo.setGroupByOrgCode(true);
                totalQo.setGroupByGradeCode(true);
                totalQo.setGroupByClassCode(true);
                totalQo.setGroupBySubjectCode(true);
                totalQo.setGroupByInviteId(true);
                totalQo.setGroupByOpponentId(true);
                totalQo.setGroupByType(true);
                totalQo.setType(type);

                List<HeroLandStatisticsDetailDP> totalSyncTotalScore = heroLandCompetitionRecordService.getTotalScore(totalQo);
                if (CollectionUtils.isEmpty(totalSyncTotalScore)) {
                    continue;
                }

                Map<String, HeroLandStatisticsDetailDP> mergeMap = totalSyncTotalScore.stream().filter(v -> v.getUserId().length() > 15 &&  v.getSubjectCode() != null).collect(Collectors.toMap(this::fetchUserKey, Function.identity(), (o, n) -> n));

                totalQo.setTopicIds(new ArrayList<>(totalSyncTotalScore.stream().map(HeroLandStatisticsDetailDP::getTopicId).map(Long::valueOf).collect(Collectors.toSet())));
                Map<String, String> topic2Subject = totalSyncTotalScore.stream().filter(v -> StringUtils.isNotBlank(v.getSubjectCode())).collect(Collectors.toMap(HeroLandStatisticsDetailDP::getTopicId, HeroLandStatisticsDetailDP::getSubjectCode, (o, n) -> o));
                Map<String, List<String>> subject2Topic = totalSyncTotalScore.stream().filter(v -> StringUtils.isNotBlank(v.getSubjectCode())).collect(Collectors.groupingBy(this::fetchSubjectKey))
                        .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, key -> new ArrayList<>(key.getValue().stream().map(this::fetchKey).collect(Collectors.toSet()))));


                try {
                    totalQo.setTopic2OrgCode(totalSyncTotalScore.stream().collect(Collectors.toMap(HeroLandStatisticsDetailDP::getTopicId,HeroLandStatisticsDetailDP::getOrgCode,(o,n)->n)));
                } catch (Exception ignored) {
                }

                totalQo.setTopic2Subject(topic2Subject);

                totalQo.setSubject2Topic(subject2Topic);
                HeroLandTopicQuestionsPageRequest heroLandTopicQuestionsPageRequest = new HeroLandTopicQuestionsPageRequest();
                heroLandTopicQuestionsPageRequest.setTopicIds(totalQo.getTopicIds());

                /*
                 *
                 *   4 答对率
                 *
                 */
                List<HeroLandStatisticsDetailDP> answerRightRate = heroLandCompetitionRecordService.getAnswerRightRate(totalQo);

                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : answerRightRate) {
                    HeroLandStatisticsDetailDP totalDP = mergeMap.get(this.fetchUserKey(heroLandStatisticsTotalDP));
                    if (totalDP != null) {
                        totalDP.setAnswerRightRate(heroLandStatisticsTotalDP.getAnswerRightRate());
                    }
                }

//                /*
//                 *5 胜率
//                 */
//                List<HeroLandStatisticsDetailDP> winRate = heroLandCompetitionRecordService.getWinRate(totalQo);
//                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDp : winRate) {
//                    HeroLandStatisticsDetailDP dp = mergeMap.get(this.fetchUserKey(heroLandStatisticsTotalDp));
//                    if (dp != null) {
//                        dp.setWinRate(heroLandStatisticsTotalDp.getWinRate());
//                    }
//                }

                /*
                 * 6 总时长
                 */
                List<HeroLandStatisticsDetailDP> totalTime = heroLandCompetitionRecordService.getTotalTime(totalQo);
                for (HeroLandStatisticsDetailDP holistically : totalTime) {
                    HeroLandStatisticsDetailDP total = mergeMap.get(this.fetchUserKey(holistically));
                    if (total != null) {
                        total.setTotalTime(holistically.getTotalTime());
                    }
                }
                Collection<HeroLandStatisticsDetailDP> values = mergeMap.values();

                List<HeroLandTopicDto> topics = heroLandQuestionService.getTopics(heroLandTopicQuestionsPageRequest);
                if (topics != null) {
                    try {
                        Map<Long, String> subjectRefect = topics.stream().collect(Collectors.toMap(HeroLandTopicDto::getTopicId, HeroLandTopicDto::getCourseCode, (o, n) -> n));


                        for (HeroLandStatisticsDetailDP v : values) {
                            if (v.getSubjectCode() == null && v.getTopicId() != null) {
                                v.setSubjectCode(subjectRefect.get(Long.valueOf(v.getTopicId())));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                // 查询字典
                List<String> departmentCode = Lists.newArrayList();
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getClassCode).collect(Collectors.toList()));
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getGradeCode).collect(Collectors.toList()));
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getSubjectCode).collect(Collectors.toList()));
                List<HerolandBasicDataDP> adminDataList = heroLandAdminService.getDictInfoByKeys(departmentCode);
                Map<String, HerolandBasicDataDP> adminDataMap = adminDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity(), (o, n) -> o));


                // 查询人信息
                PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
                platformSysUserQO.setNeedPage(false);
                platformSysUserQO.setUserIds(values.stream().map(HeroLandStatisticsDetailDP::getUserId).distinct().collect(Collectors.toList()));
                RpcResult<List<PlatformSysUserDP>> userList = null;
                try {
                    userList = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, PlatformSysUserDP> userMap = null;
                if (userList != null  && !CollectionUtils.isEmpty(userList.getData())) {
                    userMap = userList.getData().stream().collect(Collectors.toMap(PlatformSysUserDP::getUserId, Function.identity(), (o, n) -> o));
                }
                Map<String, PlatformSysUserDP> finalUserMap = userMap;
                values.forEach(v -> {
                    v.setHistory(false);
                    if (!CollectionUtils.isEmpty(finalUserMap)) {
                        PlatformSysUserDP platformSysUser = finalUserMap.get(v.getUserId());
                        if (platformSysUser!=null){
                            v.setUserName(platformSysUser.getUserName());
                        }
                    }
                    if (!CollectionUtils.isEmpty(adminDataMap)) {
                        v.setGradeName(adminDataMap.containsKey(v.getGradeCode()) ? adminDataMap.get(v.getGradeCode()).getDictValue() : "");
                        v.setClassName(adminDataMap.containsKey(v.getClassCode()) ? adminDataMap.get(v.getClassCode()).getDictValue() : "");
                        v.setSubjectName(adminDataMap.containsKey(v.getSubjectCode()) ? adminDataMap.get(v.getSubjectCode()).getDictValue() : "");

                    }

                });
                heroLandCompetitionStatisticsService.saveStatisticsTotalAndDetail(null, new ArrayList<>(values));
            }
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            redisService.del("statistics_redis_key");
        }

        log.info("end statistics =================");


    }

    private String fetchKey(HeroLandStatisticsDetailDP detailDP) {
        return detailDP.getOrgCode() + detailDP.getTopicId();
    }

    private String fetchSubjectKey(HeroLandStatisticsDetailDP detailDP) {
        return detailDP.getOrgCode() + detailDP.getSubjectCode();
    }

    private String fetchUserKey(HeroLandStatisticsDetailDP detailDP) {
        return detailDP.getUserId() + detailDP.getSubjectCode();
    }


}
