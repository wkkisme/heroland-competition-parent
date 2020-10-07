package com.heroland.competition.service.impl.task;

import com.anycommon.cache.service.RedisService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
     *
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void statistics() {
        log.info("start  statistics =================");
        if (!redisService.setNx("statistics_redis_key", true, "PT1h")) {
            log.info("start statistics is lock");
            return;
        }
        // 1 先清除历史版本

        try {
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
                Map<String, HeroLandStatisticsDetailDP> mergeMap = Maps.newHashMapWithExpectedSize(totalSyncTotalScore.size());
                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : totalSyncTotalScore) {
                    mergeMap.put(heroLandStatisticsTotalDP.getUserId(), heroLandStatisticsTotalDP);
                }

                /*
                 *
                 *   4 答对率
                 *
                 */
                List<HeroLandStatisticsDetailDP> answerRightRate = heroLandCompetitionRecordService.getAnswerRightRate(totalQo);

                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : answerRightRate) {
                    HeroLandStatisticsDetailDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getUserId());
                    if (totalDP != null) {
                        totalDP.setAnswerRightRate(heroLandStatisticsTotalDP.getAnswerRightRate());
                    }
                }

                /*
                 *  3 完成率
                 */
                List<HeroLandStatisticsDetailDP> completeRate = heroLandCompetitionRecordService.getCompleteRate(totalQo);

                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : completeRate) {
                    HeroLandStatisticsDetailDP dp = mergeMap.get(heroLandStatisticsTotalDP.getUserId());
                    if (dp != null) {
                        dp.setCompleteRate(heroLandStatisticsTotalDP.getCompleteRate());
                    }
                }

                /*
                 *5 胜率
                 */
                List<HeroLandStatisticsDetailDP> winRate = heroLandCompetitionRecordService.getWinRate(totalQo);
                for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDp : winRate) {
                    HeroLandStatisticsDetailDP dp = mergeMap.get(heroLandStatisticsTotalDp.getUserId());
                    if (dp != null) {
                        dp.setWinRate(heroLandStatisticsTotalDp.getWinRate());
                    }
                }

                /*
                 * 6 总时长
                 */
                List<HeroLandStatisticsDetailDP> totalTime = heroLandCompetitionRecordService.getTotalTime(totalQo);
                for (HeroLandStatisticsDetailDP holistically : totalTime) {
                    HeroLandStatisticsDetailDP total = mergeMap.get(holistically.getUserId());
                    if (total != null) {
                        total.setTotalTime(holistically.getTotalTime());
                    }
                }


                Collection<HeroLandStatisticsDetailDP> values = mergeMap.values();

                // 查询字典
                List<String> departmentCode = Lists.newArrayList();
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getClassCode).collect(Collectors.toList()));
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getGradeCode).collect(Collectors.toList()));
                departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getSubjectCode).collect(Collectors.toList()));
                List<HerolandBasicDataDP> adminDataList = heroLandAdminService.getDictInfoByKeys(departmentCode);
                Map<String, HerolandBasicDataDP> adminDataMap = adminDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity(), (o, n) -> o));


                // 查询人信息
                PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
                platformSysUserQO.setUserIds(values.stream().map(HeroLandStatisticsDetailDP::getUserId).collect(Collectors.toList()));
                RpcResult<List<PlatformSysUserDP>> userList = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
                values.forEach(v -> {
                    v.setHistory(false);
                    if (!CollectionUtils.isEmpty(userList.getData())) {
                        userList.getData().forEach(u -> {
                            if (v.getUserId().equalsIgnoreCase(u.getUserId())) {
                                v.setUserName(u.getUserName());
                            }
//                            v.setOrgCode(u.getOrgCode());
                        });
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
        } finally {
            redisService.del("statistics_redis_key");
        }

        log.info("end statistics =================");


    }


}
