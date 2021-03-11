package com.heroland.competition.service.impl.task;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.constants.TimeIntervalUnit;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BatchUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandStatisticsWordDP;
import com.heroland.competition.domain.dto.WorldStatisticDto;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
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
    @Resource
    private HerolandStatisticsLogMapper herolandStatisticsLogMapper;


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
//    @Scheduled(cron = "0 0 1 ? * *")
    @Scheduled(fixedRate = 30000)
    public void statistics() {
        Date now = new Date();

        String key = "world_statistics_redis_key" + DateUtils.date2String(now, DateUtils.PATTERN_DATE);
        //todo todo
//        if (!redisService.setNx(key,true,"PT3H")){
//            return;
//        }
        List<Long> ignoreTopicIds = Lists.newArrayList();
        try {
            // TODO: 2020/11/21
//            Date endDate = DateUtils.formatDate(now, DateUtils.PATTERN_DATE);
            Date endDate = DateUtils.formatDate(now, DateUtils.PATTERN_YYYY_MM_DD_HH_MM);
            Date startDate = DateUtils.plusDate(endDate, -10, TimeIntervalUnit.DAY);
            //1 获取零点前结束的所有世界赛
            HeroLandTopicGroupExample example = new HeroLandTopicGroupExample();
            HeroLandTopicGroupExample.Criteria criteria = example.createCriteria();
            criteria.andEndTimeLessThan(endDate).andStartTimeGreaterThan(startDate).andTypeEqualTo(5).andIsDeletedEqualTo(false);
            List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(example);
            ignoreTopicIds = doTask(heroLandTopicGroups);
        }catch (Exception e){
            log.error("statistics for world error", e);
        }finally {
            //对可以忽略的topicId保存到日志表中 1 可以忽略 0 正常
            saveLog(ignoreTopicIds, 1, true);
            redisService.del(key);
        }

    }

    public List<Long> doTask(List<HeroLandTopicGroup> heroLandTopicGroups){
        List<Long> ignoreTopicIds = Lists.newArrayList();
        if (CollectionUtils.isEmpty(heroLandTopicGroups)){
            return ignoreTopicIds;
        }
        Map<Long, HeroLandTopicGroup> topicGroupMap = heroLandTopicGroups.stream().collect(Collectors.toMap(HeroLandTopicGroup::getId, Function.identity()));
        // 2 根据报名人员名单去结果里一个一个查询世界赛每道题的结果
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        // 初始化年级和科目信息
        Map<String, String> basicData = initBasicData();
        HerolandStatisticsLogExample logExample = new HerolandStatisticsLogExample();
        HerolandStatisticsLogExample.Criteria logExampleCriteria = logExample.createCriteria();
        logExampleCriteria.andTopicIdIn(topicIds).andIsFinishedEqualTo(true).andIsDeletedEqualTo(false);
        List<HerolandStatisticsLog> herolandStatisticsLogs = herolandStatisticsLogMapper.selectByExample(logExample);
        List<Long> hasFinished = herolandStatisticsLogs.stream().map(HerolandStatisticsLog::getTopicId).distinct().collect(Collectors.toList());
        for (Long topicId : topicIds) {
            if (hasFinished.contains(topicId)){
                continue;
            }
            //step1 查询报名人数 获取报名人员名单
            List<String> userIds = herolandTopicJoinUserMapper.selectJoinUsersByTopicId(topicId);
            if (CollectionUtils.isEmpty(userIds)){
                ignoreTopicIds.add(topicId);
                continue;
            }
            //step2 查询年级与科目信息 获取科目信息
            HerolandTopicGroupPartExample partExample = new HerolandTopicGroupPartExample();
            HerolandTopicGroupPartExample.Criteria partExampleCriteria = partExample.createCriteria();
            partExampleCriteria.andTopicIdEqualTo(topicId);
            List<HerolandTopicGroupPart> topicGroupParts = herolandTopicGroupPartMapper.selectByExample(partExample);
            if (CollectionUtils.isEmpty(topicGroupParts)){
                ignoreTopicIds.add(topicId);
                continue;
            }
            String gradeCode = topicGroupParts.get(0).getGradeCode();
            //step3 查询题目信息 获取每科的题目数
            List<HerolandTopicQuestion> questions = herolandTopicQuestionMapper.selectByTopics(Lists.newArrayList(topicId), null);
            if (CollectionUtils.isEmpty(questions)){
                ignoreTopicIds.add(topicId);
                continue;
            }
            List<Long> questionIds = questions.stream().map(HerolandTopicQuestion::getQuestionId).collect(Collectors.toList());
            List<HerolandQuestionBank> questionBanks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);
            Map<String, List<HerolandQuestionBank>> courseQuestionMap = questionBanks.stream().collect(Collectors.groupingBy(HerolandQuestionBank::getCourse));
            Map<String, Integer> subjectQuestionCountMap = Maps.newHashMap();
            topicGroupParts.stream().forEach(e -> {
                if (!subjectQuestionCountMap.containsKey(e.getCourseCode())){
                    //只有题目数大于0才会统计写进统计表中
                    if (courseQuestionMap.containsKey(e.getCourseCode()) && courseQuestionMap.get(e.getCourseCode()).size() > 0){
                        subjectQuestionCountMap.put(e.getCourseCode(), courseQuestionMap.get(e.getCourseCode()).size());
                    }
                }
            });
            //根据科目分类后进行的统计组装信息 针对的是一个用户id
            List<WorldStatisticDto> allWordDPS = Lists.newArrayList();
            for (String userId : userIds){
                HeroLandQuestionRecordDetailExample recordDetailExample = new HeroLandQuestionRecordDetailExample();
                HeroLandQuestionRecordDetailExample.Criteria recordDetailExampleCriteria = recordDetailExample.createCriteria();
                recordDetailExampleCriteria.andUserIdEqualTo(userId).andTopicIdEqualTo(topicId+"").andIsDeletedEqualTo(false);
                List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = heroLandQuestionRecordDetailMapper.selectByExample(recordDetailExample);
                if (CollectionUtils.isEmpty(heroLandQuestionRecordDetails)){
                    //如果没有答题记录
                    continue;
                }
                WorldStatisticDto dto = computeWorldStatistic(heroLandQuestionRecordDetails, userId, gradeCode, topicGroupMap.get(topicId), subjectQuestionCountMap, basicData);
                allWordDPS.add(dto);
            }
            if (CollectionUtils.isEmpty(allWordDPS)){
                //如果所有的都没有记录
                ignoreTopicIds.add(topicId);
                continue;
            }
            //剩余信息组装 - 排名，胜率，用户名称
            buildExtInfo(allWordDPS, subjectQuestionCountMap, userIds.size());
            batchSearchUser(allWordDPS);
            allWordDPS.stream().forEach(userWorldDP -> {
                //对一个学生进行批量保存
                HerolandStatisticsWordExample wordExample = new HerolandStatisticsWordExample();
                HerolandStatisticsWordExample.Criteria wordExampleCriteria = wordExample.createCriteria();
                wordExampleCriteria.andUserIdEqualTo(userWorldDP.getUserId()).andTopicIdEqualTo(topicId);
                List<HerolandStatisticsWord> wordsByUserId = herolandStatisticsWordMapper.selectByExample(wordExample);
                if (!CollectionUtils.isEmpty(wordsByUserId)){
                    return;
                }
                List<HerolandStatisticsWord> herolandStatisticsWords = BeanCopyUtils.copyArrayByJSON(userWorldDP.getWordDPS(), HerolandStatisticsWord.class);
                herolandStatisticsWordMapper.batchInsert(herolandStatisticsWords);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
                if (userWorldDP.getWordDPForSingle() != null){
                    HerolandStatisticsWord herolandStatisticsWord = BeanCopyUtils.copyByJSON(userWorldDP.getWordDPForSingle(), HerolandStatisticsWord.class);
                    herolandStatisticsWordMapper.insertSelective(herolandStatisticsWord);
                }
            });
            saveLog(Lists.newArrayList(topicId), 0, true);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return ignoreTopicIds;
    }

    private WorldStatisticDto computeWorldStatistic(List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails, String userId, String gradeCode, HeroLandTopicGroup topicGroup, Map<String, Integer> subjectQuestionCountMap, Map<String, String> basicData){
        WorldStatisticDto dto = new WorldStatisticDto();
        dto.setUserId(userId);
        dto.setTopicId(topicGroup.getId());
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
            wordDP.setStatisticType(TopicJoinConstant.statisic_type_course);
            //总得分
            wordDP.setTotalScore(entry.getValue().stream().mapToInt(HeroLandQuestionRecordDetail::getScore).sum());
            //平均分
            wordDP.setAverageScore((wordDP.getTotalScore() * 1.0/questionCount));
            //排名,等所有的人员计算出来后才能排名

            //完成率
//            wordDP.setCompleteRate((wordDP.getTotalScore() * 1.0 / (questionCount * topicGroup.getCountLimit())));
            wordDP.setCompleteRate((entry.getValue().size()) * 1.0/questionCount);
            //正确率
            long correctCount = entry.getValue().stream().filter(e -> Objects.equals(e.getCorrectAnswer(), Boolean.TRUE)).count();
            wordDP.setAnswerRightRate(correctCount * 1.0 / questionCount);
            //胜率，等所有的人员计算出来后才能排名

            //总时长
            long sum = entry.getValue().stream().mapToLong(e -> (e.getEndDate().getTime() - e.getBeginDate().getTime()) / 1000).sum();
            if (sum <= 0){
                sum = 1L;
            }
            wordDP.setTotalTime((int)sum);
            wordDPS.add(wordDP);
            dto.getSubjectScoreMap().put(entry.getKey(), wordDP.getTotalScore());

        }
        dto.setWordDPS(wordDPS);
        //计算整个比赛的信息
        int totalQtNum = subjectQuestionCountMap.values().stream().mapToInt(Integer::intValue).sum();
        HerolandStatisticsWordDP totalWorld = new HerolandStatisticsWordDP();
        totalWorld.setUserId(userId);
        totalWorld.setTopicId(topicGroup.getId());
        totalWorld.setStartTime(topicGroup.getStartTime());
        totalWorld.setEndTime(topicGroup.getEndTime());
        totalWorld.setGradeCode(gradeCode);
        totalWorld.setGradeName(basicData.containsKey(gradeCode) ? basicData.get(gradeCode) : "");
        totalWorld.setType(5);
        totalWorld.setStatisticType(TopicJoinConstant.statisic_type_total);
        //总得分
        totalWorld.setTotalScore(dto.getWordDPS().stream().mapToInt(HerolandStatisticsWordDP::getTotalScore).sum());
        //平均分
        totalWorld.setAverageScore((totalWorld.getTotalScore() * 1.0/totalQtNum));
        //排名,不提供，有可能需要根据分数，正确率啥的排
        //完成率
//        totalWorld.setCompleteRate((totalWorld.getTotalScore() * 1.0 / (totalQtNum * topicGroup.getCountLimit())));
        totalWorld.setCompleteRate( heroLandQuestionRecordDetails.size() * 1.0 / totalQtNum);
        //正确率 按照科目进行平均
        double totalCorrectRate = dto.getWordDPS().stream().mapToDouble(HerolandStatisticsWordDP::getAnswerRightRate).sum();
        int subjectCount = (int)subjectQuestionCountMap.keySet().stream().map(String::toString).count();
        totalWorld.setAnswerRightRate(totalCorrectRate * 1.0 / subjectCount);
        //胜率，等所有的人员计算出来后才能排名,所以也不提供
        //总时长
        totalWorld.setTotalTime(dto.getWordDPS().stream().mapToInt(HerolandStatisticsWordDP::getTotalTime).sum());
        dto.setWordDPForSingle(totalWorld);
        return dto;
    }

    private void buildExtInfo(List<WorldStatisticDto> allWordDPS, Map<String, Integer> subjectQuestionCountMap, Integer userCount){
        for (Map.Entry<String, Integer> entry : subjectQuestionCountMap.entrySet()){
            List<Integer> allScore = Lists.newArrayList();
            allWordDPS.stream().forEach(e -> {
                Integer score = e.getSubjectScoreMap().get(entry.getKey());
                allScore.add(score);
            });

            List<Integer> sorted = allScore.stream().filter(Objects::nonNull).sorted((o1, o2) -> {
                return (o2 - o1);
            }).collect(Collectors.toList());
            //如果分数个数小于报名人数，则后面补0,说明有很多人没有参与答题
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

        List<Integer> scoreList = Lists.newArrayList();
        allWordDPS.stream().forEach(e -> {
            if (e.getWordDPForSingle() != null){
                scoreList.add(e.getWordDPForSingle().getTotalScore());
            }
        });

        List<Integer> sorted = scoreList.stream().sorted((o1, o2) -> {
            return (o2 - o1);
        }).collect(Collectors.toList());
        //如果分数个数小于报名人数，则后面补0,说明有很多人没有参与答题
        if (sorted.size() < userCount){
            for (int i = 0; i < (userCount-sorted.size()); i++){
                sorted.add(0);
            }
        }
        allWordDPS.stream().forEach(e -> {
                HerolandStatisticsWordDP wordDP = e.getWordDPForSingle();
                if (wordDP != null){
                    wordDP.setTotalRank(sorted.indexOf(wordDP.getTotalScore())+1);
                    wordDP.setWinRate(1 - ((wordDP.getTotalRank()-1)*1.0/userCount));
                }
        });
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

    /**
     * 分批保存userName
     * 避免一次上万数据量的查询
     * @return
     */
    private void batchSearchUser(List<WorldStatisticDto> allWordDPS){
       //一次500数据量查询
        List<List<WorldStatisticDto>> split500 = BatchUtils.split(allWordDPS, 500);
        split500.stream().forEach(e -> {
            // 查询人信息
            PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
            platformSysUserQO.setUserIds(e.stream().map(WorldStatisticDto::getUserId).collect(Collectors.toList()));
            RpcResult<List<PlatformSysUserDP>> userList = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
            if (userList.isSuccess() && !CollectionUtils.isEmpty(userList.getData())){
                Map<String, List<PlatformSysUserDP>> userIdMap = userList.getData().stream().collect(Collectors.groupingBy(PlatformSysUserDP::getUserId));
                e.stream().forEach(worldStatisticDto -> {
                    boolean hasUser = userIdMap.containsKey(worldStatisticDto.getUserId());
                    worldStatisticDto.getWordDPS().stream().forEach(herolandStatisticsWordDP -> {
                        herolandStatisticsWordDP.setUserName(hasUser ? userIdMap.get(herolandStatisticsWordDP.getUserId()).get(0).getUserName() : "");
                        herolandStatisticsWordDP.setSchoolCode(hasUser ? userIdMap.get(herolandStatisticsWordDP.getUserId()).get(0).getOrgCode() : "");
                        herolandStatisticsWordDP.setSchoolName(hasUser ? userIdMap.get(herolandStatisticsWordDP.getUserId()).get(0).getSchoolName() : "");
                    });
                    if (worldStatisticDto.getWordDPForSingle() != null){
                        worldStatisticDto.getWordDPForSingle().setUserName(hasUser ? userIdMap.get(worldStatisticDto.getUserId()).get(0).getUserName() : "");
                        worldStatisticDto.getWordDPForSingle().setSchoolCode(hasUser ? userIdMap.get(worldStatisticDto.getUserId()).get(0).getOrgCode() : "");
                        worldStatisticDto.getWordDPForSingle().setSchoolName(hasUser ? userIdMap.get(worldStatisticDto.getUserId()).get(0).getSchoolName() : "");
                    }
                });
            }


        });

    }

    private void saveLog(List<Long> topicIds, Integer type, Boolean isFinished){
        if (CollectionUtils.isEmpty(topicIds)){
            return;
        }
        List<HerolandStatisticsLog> logList = topicIds.stream().map(e -> {
            HerolandStatisticsLog log = new HerolandStatisticsLog();
            log.setTopicId(e);
            log.setType(type);
            log.setIsFinished(isFinished);
            return log;
        }).collect(Collectors.toList());
        herolandStatisticsLogMapper.batchInsert(logList);
    }

}
