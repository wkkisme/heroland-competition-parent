package com.heroland.competition.service.statistics.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.TopicTypeConstants;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.GroupByEnum;
import com.heroland.competition.common.enums.OrderByEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.HeroLevelUtils;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsTotalExtMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.*;
import com.heroland.competition.domain.dto.HeroLandQuestionTopicListForStatisticDto;
import com.heroland.competition.domain.qo.AnswerQuestionRecordStatisticQO;
import com.heroland.competition.domain.qo.AnswerResultQO;
import com.heroland.competition.domain.qo.CourseFinishStatisticQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 统计
 *
 * @author wangkai
 */
@Service
@Slf4j
public class HeroLandCompetitionStatisticsServiceImpl implements HeroLandCompetitionStatisticsService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandCompetitionStatisticsServiceImpl.class);

    @Resource
    private HeroLandStatisticsTotalExtMapper heroLandStatisticsTotalExtMapper;

    @Resource
    private HeroLandStatisticsDetailExtMapper heroLandStatisticsDetailExtMapper;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLevelUtils heroLevelUtils;

    @Resource
    private HeroLandCompetitionRecordExtMapper competitionRecordExtMapper;

    @Resource
    private HeroLandQuestionRecordDetailExtMapper questionRecordDetailExtMapper;

    @Override
    public ResponseBody<Boolean> saveStatisticsTotal(List<HeroLandStatisticsTotalDP> dp) {
        AssertUtils.assertThat(CollectionUtils.isEmpty(dp));

        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : dp) {
            heroLandStatisticsTotalDP.addCheck();
        }
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            List<HeroLandStatisticsTotal> heroLandStatisticsTotals = BeanUtil.queryListConversion(dp, HeroLandStatisticsTotal.class);
            for (HeroLandStatisticsTotal heroLandStatisticsTotal : heroLandStatisticsTotals) {
                heroLandStatisticsTotalExtMapper.insert(heroLandStatisticsTotal);
            }
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }
//
//    @Override
//    public ResponseBody<List<HeroLandStatisticsTotalDP>> getCompetitionsTatal(HeroLandStatisticsTotalQO qo) {
//        if (qo.getOrderByField() != null) {
//            qo.setOrderField(qo.getOrderByField().getOrderByFiled());
//            qo.setRankField(qo.getOrderByField().getFiled());
//        } else {
//            qo.setOrderField(OrderByEnum.TOTAL_SCORE_DESC.getOrderByFiled());
//            qo.setRankField(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
//        }
//
//        try {
//            return ResponseBodyWrapper
//                    .successListWrapper(heroLandStatisticsTotalExtMapper.selectStatisticsByRank(qo),
//                            heroLandStatisticsTotalExtMapper.countStatisticsByRank(qo), qo, HeroLandStatisticsTotalDP.class);
//        } catch (Exception e) {
//            log.error("", e);
//            ResponseBodyWrapper.failSysException();
//        }
//        return null;
//    }

    @Override
    public ResponseBody<Boolean> saveStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> totalDPS, List<HeroLandStatisticsDetailDP> detailDPS) {
        if (!CollectionUtils.isEmpty(totalDPS)) {
            for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : totalDPS) {
                heroLandStatisticsTotalDP.addTotalAndDetailCheck();
            }
            saveStatisticsTotal(totalDPS);
        }
        if (!CollectionUtils.isEmpty(detailDPS)) {
            for (HeroLandStatisticsDetailDP detail : detailDPS) {
                try {
                    detail.addDetailCheck();
                    heroLandStatisticsDetailExtMapper.insert(BeanUtil.insertConversion(detail, new HeroLandStatisticsDetail()));
                } catch (Exception e) {
                    log.error("", e);
                    ResponseBodyWrapper.failSysException();
                }
            }
        }

        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateStatisticsTotal(List<HeroLandStatisticsTotalDP> dp) {
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : dp) {
            heroLandStatisticsTotalDP.updateCheck();
            try {
                HeroLandStatisticsTotalExample totalExample = new HeroLandStatisticsTotalExample();
                HeroLandStatisticsTotalExample.Criteria criteria = totalExample.createCriteria();
                criteria.andTotalIdEqualTo(heroLandStatisticsTotalDP.getTotalId());
                criteria.andIdEqualTo(heroLandStatisticsTotalDP.getId());
                criteria.andIsDeletedEqualTo(false);
                criteria.andOrgCodeEqualTo(heroLandStatisticsTotalDP.getOrgCode());
                heroLandStatisticsTotalExtMapper.updateByExample(BeanUtil.updateConversion(heroLandStatisticsTotalDP, new HeroLandStatisticsTotal()), totalExample);
            } catch (Exception e) {
                log.error("", e);
                ResponseBodyWrapper.failSysException();
            }
        }

        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateHistoryStatisticsTotalAndDetailByQO(HeroLandStatisticsTotalQO qo) {
        HeroLandStatisticsTotal heroLandStatisticsTotal = new HeroLandStatisticsTotal();
        heroLandStatisticsTotal.setHistory(true);
        HeroLandStatisticsTotalExample heroLandStatisticsTotalExample = new HeroLandStatisticsTotalExample();
        MybatisCriteriaConditionUtil.createExample(heroLandStatisticsTotalExample.createCriteria(), qo);

        heroLandStatisticsTotalExtMapper.updateByExampleSelective(heroLandStatisticsTotal, heroLandStatisticsTotalExample);

        HeroLandStatisticsDetail heroLandStatisticsDetail = new HeroLandStatisticsDetail();
        heroLandStatisticsDetail.setHistory(true);
        HeroLandStatisticsDetailExample heroLandStatisticsDetailExample = new HeroLandStatisticsDetailExample();
        MybatisCriteriaConditionUtil.createExample(heroLandStatisticsDetailExample.createCriteria(), qo);
        heroLandStatisticsDetailExtMapper.updateByExampleSelective(heroLandStatisticsDetail, heroLandStatisticsDetailExample);

        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> dp) {

        updateStatisticsTotal(dp);

        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : dp) {
            heroLandStatisticsTotalDP.updateTotalAndDetailCheck();
            for (HeroLandStatisticsDetailDP landStatisticsTotalDP : heroLandStatisticsTotalDP.getDetails()) {


                try {
                    HeroLandStatisticsDetailExample heroLandStatisticsDetailExample = new HeroLandStatisticsDetailExample();
                    HeroLandStatisticsDetailExample.Criteria criteria = heroLandStatisticsDetailExample.createCriteria();
                    criteria.andIsDeletedEqualTo(false);
                    criteria.andDetailIdEqualTo(landStatisticsTotalDP.getDetailId());
                    criteria.andIdEqualTo(landStatisticsTotalDP.getId());
                    criteria.andOrgCodeEqualTo(landStatisticsTotalDP.getOrgCode());
                    heroLandStatisticsDetailExtMapper.updateByExample(BeanUtil.updateConversion(heroLandStatisticsTotalDP, new HeroLandStatisticsDetail()), heroLandStatisticsDetailExample);
                } catch (Exception e) {
                    log.error("", e);
                    ResponseBodyWrapper.failSysException();
                }
            }
        }
        return new ResponseBody<>();
    }


    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        qo.checkType();
        if (qo.getOrderByField() != null) {
            qo.setOrderField(qo.getOrderByField().getOrderByFiled());
            qo.setRankField(qo.getOrderByField().getFiled());
        } else {
            qo.setOrderField(OrderByEnum.TOTAL_SCORE_DESC.getOrderByFiled());
            qo.setRankField(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
        }

        ResponseBody<List<HeroLandStatisticsDetailDP>> result = ResponseBodyWrapper
                .successListWrapper(heroLandStatisticsDetailExtMapper.selectStatisticsByRank(qo),
                        heroLandStatisticsDetailExtMapper.countStatisticsByRank(qo), qo, HeroLandStatisticsDetailDP.class);
        if (qo.getType().equals(CompetitionEnum.SYNC.getType())) {
            // 同步作业赛时不需要班级排名
            return result;
        }
        try {

//            AssertUtils.notBlank(qo.getClassCode());
            List<String> userIds = result.getData().stream().map(HeroLandStatisticsDetailDP::getUserId).collect(Collectors.toList());
            qo.setUserIds(userIds);
            qo.setRankField(GroupByEnum.class_code.getFiled());
            List<HeroLandStatisticsDetailAll> classRank = heroLandStatisticsDetailExtMapper.selectStatisticsByRank(qo);


            result.getData().forEach(v -> {
                classRank.forEach(s -> {
                    if (v.getUserId().equals(s.getUserId())) {
                        v.setClassRank(s.getRank());
                    }

                });
            });

            return result;
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return null;
    }


    /**
     * 获取科目相关统计
     * 时间不多，写不出好代码，如果有后来人看到这些垃圾凑数一样的代码，不要骂我，我也是无奈哈！见谅！真的没时间改了！！
     *
     * @param qo
     * @return
     */
    @Override
    public ResponseBody<List<CompetitionCourseFinishStatisticDP>> getCourseFinishStatistic(CourseFinishStatisticQO qo) {

        HeroLandTopicQuestionForCourseRequest request = new HeroLandTopicQuestionForCourseRequest();
        BeanUtil.copyProperties(qo, request);
        List<HeroLandQuestionTopicListForStatisticDto> topicQuestionForCourseStatistics = heroLandQuestionService.getTopicQuestionForCourseStatistics(request);
        logger.info("拿到获取科目的数据,request={}, list={}", JSONObject.toJSONString(qo), JSONObject.toJSONString(topicQuestionForCourseStatistics));
        if (CollUtil.isEmpty(topicQuestionForCourseStatistics)) {
            return ResponseBodyWrapper.success();
        }
        Map<String, List<HeroLandQuestionTopicListForStatisticDto>> map = topicQuestionForCourseStatistics.stream().filter(d -> StrUtil.isNotBlank(d.getCourseCode())).collect(Collectors.groupingBy(HeroLandQuestionTopicListForStatisticDto::getCourseCode));
        List<String> topicIds = topicQuestionForCourseStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getId).map(String::valueOf).distinct().collect(Collectors.toList());
        List<HeroLandCompetitionRecord> heroLandCompetitionRecords = competitionRecordExtMapper.selectByTopicIdsAndInviterId(topicIds, qo.getUserId());
        AtomicReference<Map<String, List<HeroLandCompetitionRecord>>> competitionRecordMap = new AtomicReference<>();
        AtomicReference<Map<String, List<HeroLandQuestionRecordDetailDP>>> questionRecordMap = new AtomicReference<>();
        if (CollUtil.isNotEmpty(heroLandCompetitionRecords)) {
            competitionRecordMap.set(heroLandCompetitionRecords.stream().collect(Collectors.groupingBy(HeroLandCompetitionRecord::getTopicId)));
            List<String> competitionRecordIds = heroLandCompetitionRecords.stream().map(HeroLandCompetitionRecord::getRecordId).collect(Collectors.toList());
            List<HeroLandQuestionRecordDetailDP> heroLandQuestionRecordDetails = questionRecordDetailExtMapper.selectByCompetitionRecordId(competitionRecordIds);
            if (CollUtil.isNotEmpty(heroLandQuestionRecordDetails)) {
                questionRecordMap.set(heroLandQuestionRecordDetails.stream().collect(Collectors.groupingBy(HeroLandQuestionRecordDetailDP::getTopicId)));
            }
        }
        List<CompetitionCourseFinishStatisticDP> dps = new ArrayList<>();
        map.forEach((courseCode, questionTopicStatistics) -> {
            CompetitionCourseFinishStatisticDP dp = new CompetitionCourseFinishStatisticDP();
            HeroLandQuestionTopicListForStatisticDto dto = questionTopicStatistics.get(0);
            dp.setCourseCode(courseCode);
            dp.setCourseName(dto.getCourseName());
            dp.setClassCode(dto.getClassCode());
            dp.setQuestionNum(questionTopicStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getQuestionNum).reduce(0, Integer::sum));
            dp.setChapterCount(questionTopicStatistics.stream().map(q -> q.getChapterList().size()).reduce(0, Integer::sum));
            dp.setSectionCount(questionTopicStatistics.stream().map(q -> q.getSectionList().size()).reduce(0, Integer::sum));
            Map<Long, HeroLandQuestionTopicListForStatisticDto> statisticDtoMap = questionTopicStatistics.stream().collect(Collectors.toMap(HeroLandQuestionTopicListForStatisticDto::getId, Function.identity(), (o, n) -> n));
            if (MapUtil.isNotEmpty(competitionRecordMap.get())) {
                statisticDtoMap.forEach((topicId, statisticDto) -> {
                    List<HeroLandCompetitionRecord> competitionRecords = competitionRecordMap.get().get(String.valueOf(topicId));
                    long winCount = competitionRecords.stream().map(HeroLandCompetitionRecord::getResult).filter(c -> c.equals(0)).count();
                    // 胜率
                    BigDecimal winRate = new BigDecimal(winCount).divide(new BigDecimal(competitionRecords.size()), 2, RoundingMode.HALF_UP);
                    dp.setWinRate(winRate);
                    if (ObjectUtil.isNotNull(questionRecordMap.get()) && CollUtil.isNotEmpty(questionRecordMap.get())) {
                        List<HeroLandQuestionRecordDetailDP> questionRecordDetails = questionRecordMap.get().get(String.valueOf(topicId));
                        // 完成情况
                        dp.setFinishQuestion(questionRecordDetails.size());
                    }
                    // TODO 完成多少节 不知道怎么统计
                });
            }
            dps.add(dp);
        });
        return ResponseBodyWrapper.successWrapper(dps);
    }

    /**
     * 获取答题记录
     * 时间不多，写不出好代码，如果有后来人看到这些垃圾凑数一样的代码，不要骂我，我也是无奈哈！见谅！真的没时间改了！！
     *
     * @param qo
     * @return
     */
    @Override
    public ResponseBody<List<AnswerQuestionRecordStatisticDP>> getAnswerQuestionRecordStatistic(AnswerQuestionRecordStatisticQO qo) {
        HeroLandTopicQuestionForCourseRequest request = new HeroLandTopicQuestionForCourseRequest();
        BeanUtil.copyProperties(qo, request);
        PageResponse<QuestionTopicDP> topicQuestionListPage = heroLandQuestionService.getQuestionTopic(request);
        List<QuestionTopicDP> items = topicQuestionListPage.getItems();
        logger.info("拿到获取每一个赛事下课节和知识点的数据,request={}, list={}", JSONObject.toJSONString(qo), JSONObject.toJSONString(items));

        if (CollUtil.isEmpty(items)) {
            return ResponseBodyWrapper.success();
        }

        List<String> topicIds = items.stream().map(QuestionTopicDP::getTopicId).map(String::valueOf).collect(Collectors.toList());
        List<HeroLandCompetitionRecord> heroLandCompetitionRecords = competitionRecordExtMapper.selectByTopicIdsAndInviterId(topicIds, qo.getUserId());
        AtomicReference<Map<String, HeroLandQuestionRecordDetailDP>> questionRecordMap = new AtomicReference<>();
        AtomicReference<Map<String, HeroLandCompetitionRecord>> competitionRecordMap = new AtomicReference<>();
        if (CollUtil.isNotEmpty(heroLandCompetitionRecords)) {
            competitionRecordMap.set(heroLandCompetitionRecords.stream().collect(Collectors.toMap(HeroLandCompetitionRecord::getTopicId, Function.identity(), (o, n) -> n)));
            List<String> competitionRecordIds = heroLandCompetitionRecords.stream().map(HeroLandCompetitionRecord::getRecordId).collect(Collectors.toList());
            List<HeroLandQuestionRecordDetailDP> questionRecords = questionRecordDetailExtMapper.selectByCompetitionRecordId(competitionRecordIds);
            if (CollUtil.isNotEmpty(questionRecords)) {
                questionRecordMap.set(questionRecords.stream().collect(Collectors.toMap(HeroLandQuestionRecordDetailDP::getQuestionId, Function.identity(), (o, n) -> n)));
            }
        }
        List<AnswerQuestionRecordStatisticDP> result = new ArrayList<>();
        // 作业赛统计
        if (TopicTypeConstants.SYNC_COMPETITION.equals(qo.getType())) {
            HeroLandTopicQuestionsPageRequest pageRequest = new HeroLandTopicQuestionsPageRequest();
            pageRequest.setTopicIds(topicIds.stream().map(Long::valueOf).collect(Collectors.toList()));
            pageRequest.setPageSize(Integer.MAX_VALUE);
            items.forEach(questionTopicDP -> {
                AnswerQuestionRecordStatisticDP dp = new AnswerQuestionRecordStatisticDP();
                dp.setQuestionId(questionTopicDP.getQuestionId());
                dp.setTopicId(questionTopicDP.getTopicId());
                dp.setDiff(questionTopicDP.getTopicDiff());
                dp.setType(questionTopicDP.getType());
                dp.setKnowledge(questionTopicDP.getKnowledges());
                if (MapUtil.isNotEmpty(competitionRecordMap.get())) {
                    HeroLandCompetitionRecord heroLandCompetitionRecord = competitionRecordMap.get().get(String.valueOf(questionTopicDP.getTopicId()));
                    if (ObjectUtil.isNotNull(heroLandCompetitionRecord)) {
                        dp.setResult(heroLandCompetitionRecord.getResult());
                        dp.setOpponentLevel(heroLandCompetitionRecord.getOpponentLevel());
                        dp.setOpponent(heroLevelUtils.compare(heroLandCompetitionRecord.getInviteLevel(), heroLandCompetitionRecord.getOpponentLevel()));
                        Integer recordResult = heroLandCompetitionRecord.getResult();
                        if (heroLandCompetitionRecord.getInviteId().equals(qo.getUserId())) {
                            if (ObjectUtil.isNotNull(recordResult)) {
                                dp.setResult(recordResult.equals(0) ? 0 : recordResult.equals(2) ? 2 : 1);
                            }
                        } else {
                            if (ObjectUtil.isNotNull(result)) {
                                dp.setResult(recordResult.equals(1) ? 0 : recordResult.equals(2) ? 2 : 1);
                            }
                        }
                    }
                }
                dp.setTopicName(questionTopicDP.getTopicName());
                if (ObjectUtil.isNotNull(questionRecordMap.get())) {
                    HeroLandQuestionRecordDetailDP questionRecordDetail = questionRecordMap.get().get(String.valueOf(questionTopicDP.getQuestionId()));
                    if (ObjectUtil.isNotNull(questionRecordDetail)) {
                        // 如果是同步作业赛，题只能有一个
                        dp.setIsCorrectAnswer(questionRecordDetail.isCorrectAnswer());
                        dp.setScore(questionRecordDetail.getScore());
                    }
                }
                result.add(dp);
            });
        } else {
            items.forEach(questionTopicDP -> {
                AnswerQuestionRecordStatisticDP dp = new AnswerQuestionRecordStatisticDP();
                if (MapUtil.isNotEmpty(competitionRecordMap.get())) {
                    HeroLandCompetitionRecord heroLandCompetitionRecord = competitionRecordMap.get().get(questionTopicDP.getTopicId().toString());
                    if (ObjectUtil.isNotNull(heroLandCompetitionRecord)) {
                        dp.setResult(heroLandCompetitionRecord.getResult());
                        dp.setOpponentLevel(heroLandCompetitionRecord.getOpponentLevel());
                        dp.setScore(heroLandCompetitionRecord.getInviteScore());
                    }
                }
                dp.setTopicId(questionTopicDP.getTopicId());
                dp.setTopicName(questionTopicDP.getTopicName());
                dp.setType(questionTopicDP.getTopicType());
                dp.setDiff(questionTopicDP.getTopicDiff());
                result.add(dp);
            });
        }

        ResponseBody<List<AnswerQuestionRecordStatisticDP>> responseBody = new ResponseBody<>();
        responseBody.setData(result);
        responseBody.setPage(new

                Pagination(qo.getPageIndex(), qo.

                getPageSize(), topicQuestionListPage.

                getTotal()));
        return responseBody;
    }

    @Override
    public ResponseBody<AnswerCompetitionResultDP> getAnswerResult(AnswerResultQO qo) {
        HeroLandCompetitionRecord competitionRecord = competitionRecordExtMapper.selectByRecordId(qo.getCompetitionRecordId());
        AnswerCompetitionResultDP dp = new AnswerCompetitionResultDP();
        Integer result = competitionRecord.getResult();

        if (competitionRecord.getInviteId().equals(qo.getUserId())) {
            if (ObjectUtil.isNotNull(result)) {
                dp.setResult(result.equals(0) ? 0 : result.equals(2) ? 2 : 1);
            }
            dp.setTotalScore(competitionRecord.getInviteScore());
        } else {
            if (ObjectUtil.isNotNull(result)) {
                dp.setResult(result.equals(1) ? 0 : result.equals(2) ? 2 : 1);
            }
            dp.setTotalScore(competitionRecord.getOpponentScore());
        }
        // 获取题目的比赛详情
        List<HeroLandQuestionRecordDetailDP> questionRecordDetails = questionRecordDetailExtMapper.selectByCompetitionRecordId(Lists.newArrayList(qo.getCompetitionRecordId()));
        List<AnswerCompetitionResultDP.AnswerDetail> answerDetails = new ArrayList<>();
        questionRecordDetails.forEach(questionRecord -> {
            AnswerCompetitionResultDP.AnswerDetail answerDetail = new AnswerCompetitionResultDP.AnswerDetail();
            // TODO 题型
//            answerDetail.setDiff(questionRecord.ge);
            answerDetail.setIsCorrectAnswer(questionRecord.isCorrectAnswer());
            answerDetail.setScore(questionRecord.getScore());
            // TODO 用时
//            answerDetail.setUseTime();
            answerDetails.add(answerDetail);
        });
        dp.setAnswerDetails(answerDetails);
        return ResponseBodyWrapper.successWrapper(dp);
    }
}
