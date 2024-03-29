package com.heroland.competition.record;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.google.common.collect.Lists;
import com.heroland.competition.base.BaseServiceTest;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.AnswerQuestionRecordStatisticDP;
import com.heroland.competition.domain.dp.CompetitionCourseFinishStatisticDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.qo.CourseFinishStatisticQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.result.RpcResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HeroCompetitionTest extends BaseServiceTest {
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;
    @Resource
    private HeroLandQuestionService heroLandQuestionService;
//    @Test
    public void test(){

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

            Map<String, HeroLandStatisticsDetailDP> mergeMap = totalSyncTotalScore.stream().filter(v -> v.getUserId().length() > 15 && v.getSubjectCode() != null).collect(Collectors.toMap(this::fetchUserKey, Function.identity(), (o, n) -> n));

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

//            /*
//             *5 胜率
//             */
//            List<HeroLandStatisticsDetailDP> winRate = heroLandCompetitionRecordService.getWinRate(totalQo);
//            for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDp : winRate) {
//                HeroLandStatisticsDetailDP dp = mergeMap.get(this.fetchUserKey(heroLandStatisticsTotalDp));
//                if (dp != null) {
//                    dp.setWinRate(heroLandStatisticsTotalDp.getWinRate());
//                }
//            }

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
//            // 查询字典
//            List<String> departmentCode = Lists.newArrayList();
//            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getClassCode).collect(Collectors.toList()));
//            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getGradeCode).collect(Collectors.toList()));
//            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getSubjectCode).collect(Collectors.toList()));
//            List<HerolandBasicDataDP> adminDataList = heroLandAdminService.getDictInfoByKeys(departmentCode);
//            Map<String, HerolandBasicDataDP> adminDataMap = adminDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity(), (o, n) -> o));
//
//
//            // 查询人信息
//            PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
//            platformSysUserQO.setUserIds(values.stream().map(HeroLandStatisticsDetailDP::getUserId).collect(Collectors.toList()));
//            RpcResult<List<PlatformSysUserDP>> userList = null;
//            try {
//                userList = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            RpcResult<List<PlatformSysUserDP>> finalUserList = userList;
//
//            values.forEach(v -> {
//                v.setHistory(false);
//                if (finalUserList != null  && !CollectionUtils.isEmpty(finalUserList.getData())) {
//                    finalUserList.getData().forEach(u -> {
//                        if (v.getUserId().equalsIgnoreCase(u.getUserId())) {
//                            v.setUserName(u.getUserName());
//                        }
////                            v.setOrgCode(u.getOrgCode());
//                    });
//                }
//
//                if (!CollectionUtils.isEmpty(adminDataMap)) {
//                    v.setGradeName(adminDataMap.containsKey(v.getGradeCode()) ? adminDataMap.get(v.getGradeCode()).getDictValue() : "");
//                    v.setClassName(adminDataMap.containsKey(v.getClassCode()) ? adminDataMap.get(v.getClassCode()).getDictValue() : "");
//                    v.setSubjectName(adminDataMap.containsKey(v.getSubjectCode()) ? adminDataMap.get(v.getSubjectCode()).getDictValue() : "");
//
//                }
//
//            });
            List<HeroLandStatisticsDetailDP> collect = values.stream().filter(v -> StringUtils.isNotBlank(v.getUserName())).collect(Collectors.toList());
        }

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

    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;

//    @Test
    public void  checkOrder(){
        String s= "{\"type\":0,\"classCode\":\"CA-611611150051537\",\"gradeCode\":\"GA_006\",\"userId\":\"3235234182347096454001\",\"startTime\":\"\",\"endTime\":\"\",\"orgCode\":\"SH-61161114982262\"}";
        HeroLandTopicQuestionsPageRequest heroLandTopicQuestionsPageRequest = JSON.parseObject(s, HeroLandTopicQuestionsPageRequest.class);
//        ResponseBody<List<AnswerQuestionRecordStatisticDP>> answerQuestionRecordStatistic = heroLandCompetitionStatisticsService.getAnswerQuestionRecordStatistic(heroLandTopicQuestionsPageRequest);
//        System.out.println(answerQuestionRecordStatistic);

        CourseFinishStatisticQO courseFinishStatisticQO = JSON.parseObject(s, CourseFinishStatisticQO.class);
        ResponseBody<List<CompetitionCourseFinishStatisticDP>> courseFinishStatistic = heroLandCompetitionStatisticsService.getCourseFinishStatistic(courseFinishStatisticQO);
        System.out.println(courseFinishStatistic);
    }
}
