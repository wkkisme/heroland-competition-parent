package com.heroland.competition.service.impl.task;

import com.anycommon.cache.service.RedisService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.constants.TimeIntervalUnit;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandStatisticsWordDP;
import com.heroland.competition.domain.dto.WorldStatisticDto;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.domain.qo.HerolandTopicQuestionQo;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 世界赛统计
 */
@Component
@Slf4j
public class WorldStatisticsTask {
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
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Resource
    private HerolandTopicJoinUserMapper herolandTopicJoinUserMapper;

    @Resource
    private HeroLandQuestionRecordDetailMapper heroLandQuestionRecordDetailMapper;

    @Resource
    private HerolandStatisticsWordMapper herolandStatisticsWordMapper;

    @Resource
    private HerolandTopicGroupPartMapper herolandTopicGroupPartMapper;

    @Resource
    private HerolandTopicQuestionMapper herolandTopicQuestionMapper;

    @Resource
    private HerolandQuestionBankMapper herolandQuestionBankMapper;


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
     */
    @Scheduled(cron = "0 0 1 ? * *")
    public void statistics() {
        Date now = new Date();

        String key = "world_statistics_redis_key" + DateUtils.date2String(now, DateUtils.PATTERN_DATE);

        if (!redisService.setNx(key,true,"PT3H")){
            return;
        }
        Date endDate = DateUtils.formatDate(now, DateUtils.PATTERN_DATE);
        Date startDate = DateUtils.plusDate(endDate, -10, TimeIntervalUnit.DAY);
        //1 获取零点前结束的所有世界赛
        Map<String, String> basicData = initBasicData();

        HeroLandTopicGroupExample example = new HeroLandTopicGroupExample();
        HeroLandTopicGroupExample.Criteria criteria = example.createCriteria();
        criteria.andEndTimeLessThan(endDate).andStartTimeGreaterThan(startDate).andTypeEqualTo(5).andIsDeletedEqualTo(false);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(heroLandTopicGroups)){
            return;
        }
        Map<Long, HeroLandTopicGroup> topicGroupMap = heroLandTopicGroups.stream().collect(Collectors.toMap(HeroLandTopicGroup::getId, Function.identity()));

        // 2 根据报名人员名单去结果里一个一个查询世界赛每道题的结果
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());

        for (Long topicId : topicIds) {
            //step1 查询报名人数 获取报名人员名单
            List<String> userIds = herolandTopicJoinUserMapper.selectJoinUsersByTopicId(topicId);
            if (CollectionUtils.isEmpty(userIds)){
                continue;
            }
            //step2 查询年级与科目信息 获取科目信息
            HerolandTopicGroupPartExample partExample = new HerolandTopicGroupPartExample();
            HerolandTopicGroupPartExample.Criteria partExampleCriteria = partExample.createCriteria();
            partExampleCriteria.andTopicIdEqualTo(topicId);
            List<HerolandTopicGroupPart> topicGroupParts = herolandTopicGroupPartMapper.selectByExample(partExample);
            if (CollectionUtils.isEmpty(topicGroupParts)){
                continue;
            }
            String gradeCode = topicGroupParts.get(0).getGradeCode();
            //step3 查询题目信息 获取每科的题目数
            List<HerolandTopicQuestion> questions = herolandTopicQuestionMapper.selectByTopics(Lists.newArrayList(topicId), null);
            if (CollectionUtils.isEmpty(questions)){
                continue;
            }
            List<Long> questionIds = questions.stream().map(HerolandTopicQuestion::getQuestionId).collect(Collectors.toList());
            List<HerolandQuestionBank> questionBanks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);
            Map<String, List<HerolandQuestionBank>> courseQuestionMap = questionBanks.stream().collect(Collectors.groupingBy(HerolandQuestionBank::getCourse));
            Map<String, Integer> subjectQuestionCountMap = Maps.newHashMap();
            topicGroupParts.stream().forEach(e -> {
                if (!subjectQuestionCountMap.containsKey(e.getCourseCode())){
                    //只有题目数大于0才会统计写进统计表中
                    if (courseQuestionMap.get(e.getCourseCode()).size() > 0){
                        subjectQuestionCountMap.put(e.getCourseCode(), courseQuestionMap.get(e.getCourseCode()).size());
                    }
                }
            });
            //根据科目分类后进行的统计组装信息 针对的是一个用户id
            List<WorldStatisticDto> allWordDPS = Lists.newArrayList();
            for (String userId : userIds){
                HerolandStatisticsWordExample wordExample = new HerolandStatisticsWordExample();
                HerolandStatisticsWordExample.Criteria wordExampleCriteria = wordExample.createCriteria();
                wordExampleCriteria.andUserIdEqualTo(userId).andTopicIdEqualTo(topicId);
                List<HerolandStatisticsWord> wordsByUserId = herolandStatisticsWordMapper.selectByExample(wordExample);
                if (!CollectionUtils.isEmpty(wordsByUserId)){
                    continue;
                }
                HeroLandQuestionRecordDetailExample recordDetailExample = new HeroLandQuestionRecordDetailExample();
                HeroLandQuestionRecordDetailExample.Criteria recordDetailExampleCriteria = recordDetailExample.createCriteria();
                recordDetailExampleCriteria.andUserIdEqualTo(userId).andTopicIdEqualTo(topicId+"");
                List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = heroLandQuestionRecordDetailMapper.selectByExample(recordDetailExample);

                WorldStatisticDto dto = computeWorldStatistic(heroLandQuestionRecordDetails, userId, gradeCode, topicGroupMap.get(topicId), subjectQuestionCountMap, basicData);
                allWordDPS.add(dto);
            }
            //剩余信息组装 - 排名，胜率，用户名称
            buildExtInfo(allWordDPS, subjectQuestionCountMap, userIds.size());
        }

        // 3 统计


        try {
            Collection<HeroLandStatisticsDetailDP> values = null;

            // 查询字典
            List<String> departmentCode = Lists.newArrayList();
            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getClassCode).collect(Collectors.toList()));
            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getGradeCode).collect(Collectors.toList()));
            departmentCode.addAll(values.stream().map(HeroLandStatisticsDetailDP::getSubjectCode).collect(Collectors.toList()));
            List<HerolandBasicDataDP> adminDataList = heroLandAdminService.getDictInfoByKeys(departmentCode);
            Map<String, HerolandBasicDataDP> adminDataMap = adminDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity()));


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
                    });
                }
                if (!CollectionUtils.isEmpty(adminDataMap)) {
                    v.setGradeName(adminDataMap.containsKey(v.getGradeCode()) ? adminDataMap.get(v.getGradeCode()).getDictValue() : "");
                    v.setClassName(adminDataMap.containsKey(v.getClassCode()) ? adminDataMap.get(v.getGradeCode()).getDictValue() : "");
                    v.setSubjectName(adminDataMap.containsKey(v.getSubjectCode()) ? adminDataMap.get(v.getGradeCode()).getDictValue() : "");
                }
            });
            heroLandCompetitionStatisticsService.saveStatisticsTotalAndDetail(null, new ArrayList<>(values));
        } catch (Exception e) {
            log.error("", e);
        }finally {
            redisService.del("statistics_redis_key");
        }



    }

    private WorldStatisticDto computeWorldStatistic(List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails, String userId, String gradeCode, HeroLandTopicGroup topicGroup, Map<String, Integer> subjectQuestionCountMap, Map<String, String> basicData){
        WorldStatisticDto dto = new WorldStatisticDto();
        List<HerolandStatisticsWordDP> wordDPS = Lists.newArrayList();
        Map<String, List<HeroLandQuestionRecordDetail>> subjectMap = heroLandQuestionRecordDetails.stream().collect(Collectors.groupingBy(HeroLandQuestionRecordDetail::getSubjectCode));
        for (Map.Entry<String, List<HeroLandQuestionRecordDetail>> entry : subjectMap.entrySet()){
            Integer questionCount = subjectQuestionCountMap.get(entry.getKey());
            HerolandStatisticsWordDP wordDP = new HerolandStatisticsWordDP();
            wordDP.setUserId(userId);
            wordDP.setTopicId(topicGroup.getId());
            wordDP.setStartTime(topicGroup.getStartTime());
            wordDP.setEndTime(topicGroup.getEndTime());
            wordDP.setSubjectCode(entry.getKey());
            wordDP.setSubjectName(basicData.containsKey(entry.getKey()) ? basicData.get(entry.getKey()) : "");
            wordDP.setGradeCode(gradeCode);
            wordDP.setGradeName(basicData.containsKey(gradeCode) ? basicData.get(gradeCode) : "");
            wordDP.setType(5);
            //总得分
            wordDP.setTotalScore(entry.getValue().stream().mapToInt(HeroLandQuestionRecordDetail::getScore).sum());
            //平均分
            wordDP.setAverageScore((wordDP.getTotalScore() * 1.0/questionCount));
            //排名,等所有的人员计算出来后才能排名

            //得分率
            wordDP.setCompleteRate((wordDP.getTotalScore() * 1.0 / (questionCount * topicGroup.getCountLimit())));
            //正确率
            long correctCount = entry.getValue().stream().filter(e -> Objects.equals(e.getCorrectAnswer(), Boolean.TRUE)).count();
            wordDP.setAnswerRightRate(correctCount * 1.0 / questionCount);
            //胜率，等所有的人员计算出来后才能排名

            //总时长
            long sum = entry.getValue().stream().mapToLong(e -> (e.getEndDate().getTime() - e.getBeginDate().getTime()) / 1000).sum();
            if (sum <= 0){
                sum = 1L;
                wordDP.setTotalTime((int)sum);
            }
            wordDPS.add(wordDP);
            dto.setUserId(userId);
            dto.setTopicId(topicGroup.getId());
            dto.getSubjectScoreMap().put(entry.getKey(), wordDP.getTotalScore());

        }
        dto.setWordDPS(wordDPS);
        return dto;
    }

    private void buildExtInfo(List<WorldStatisticDto> allWordDPS, Map<String, Integer> subjectQuestionCountMap, Integer userCount){
        for (Map.Entry<String, Integer> entry : subjectQuestionCountMap.entrySet()){
            List<Integer> allScore = Lists.newArrayList();
            allWordDPS.stream().forEach(e -> {
                Integer score = e.getSubjectScoreMap().get(entry.getKey());
                allScore.add(score);
            });

            List<Integer> sorted = allScore.stream().sorted((o1, o2) -> {
                return (o2 - o1);
            }).collect(Collectors.toList());
            //如果分数个数小于报名人数，则后面补0
            if (sorted.size() < userCount){
                for (int i = 0; i < (userCount-sorted.size()); i++){
                    sorted.add(0);
                }
            }
            allWordDPS.stream().forEach(e -> {
                List<HerolandStatisticsWordDP> wordDPS = e.getWordDPS();
                if (!CollectionUtils.isEmpty(wordDPS)){
                    HerolandStatisticsWordDP wordDP = wordDPS.stream().filter(sub -> Objects.equals(sub.getSubjectCode(), entry.getKey())).findFirst().orElse(null);
                    if (wordDP != null){
                        wordDP.setTotalRank(sorted.indexOf(wordDP.getTotalScore())+1);
                        wordDP.setWinRate(1 - ((wordDP.getTotalRank()-1)*1.0/userCount));
                    }
                }
            });


        }

        return;
    }

    /**
     * 初始化字典数据信息
     * @return
     */
    private Map<String, String> initBasicData(){
        Map<String, String> basicMap = Maps.newHashMap();
        HerolandDataPageRequest request = new HerolandDataPageRequest();
        request.setCode("CU");
        request.setNeedPage(false);
        PageResponse<HerolandBasicDataDP> courses = heroLandAdminService.pageDataByCode(request);
        courses.getItems().stream().forEach(e -> basicMap.put(e.getDictKey(), e.getDictValue()));
        request.setCode("GA");
        PageResponse<HerolandBasicDataDP> grades = heroLandAdminService.pageDataByCode(request);
        grades.getItems().stream().forEach(e -> basicMap.put(e.getDictKey(), e.getDictValue()));
        return basicMap;
    }

    public static void main(String[] args) {
        List<Integer> integers = Lists.newArrayList(3, 5, 2, 78, 34);
        integers = integers.stream().sorted((o1, o2) -> {
            return (o2 - o1);
        }).collect(Collectors.toList());
        System.out.println(integers);
    }


}
