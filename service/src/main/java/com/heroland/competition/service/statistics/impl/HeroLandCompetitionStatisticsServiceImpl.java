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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.constants.TopicTypeConstants;
import com.heroland.competition.common.enums.*;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.HeroLevelUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.*;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.*;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.wml.P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;
    @Resource
    private HeroLandCompetitionRecordExtMapper competitionRecordExtMapper;

    @Resource
    private HeroLandQuestionRecordDetailExtMapper questionRecordDetailExtMapper;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @Resource
    private HerolandTopicGroupPartService herolandTopicGroupPartService;

    @Resource
    private HerolandStatisticsWordMapper herolandStatisticsWordMapper;

    @Resource
    private HerolandStatisticsLogMapper herolandStatisticsLogMapper;

    @Resource
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Override
    public ResponseBody<Boolean> saveStatisticsTotal(List<HeroLandStatisticsTotalDP> dp) {
        AssertUtils.assertThat(!CollectionUtils.isEmpty(dp));

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
            qo.setOrderType(OrderByEnum.getOrderType(qo.getOrderField()));
        } else {
            qo.setOrderField(OrderByEnum.TOTAL_SCORE_DESC.getOrderByFiled());
            qo.setRankField(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
        }
        if (qo.getSubjectCode() == null) {
            qo.setQueryAll(true);
        }
        List<HeroLandStatisticsDetailAll> detailAlls = heroLandStatisticsDetailExtMapper.selectStatisticsByRank(qo);
        if (qo.getUserId() == null) {
            qo.setUserId(qo.getCurrentUserId());
            List<HeroLandStatisticsDetailAll> myRank = heroLandStatisticsDetailExtMapper.selectStatisticsByRank(qo);
            if ( !CollectionUtils.isEmpty(myRank)) {
                detailAlls.add(0, myRank.get(0));
            }
            qo.setUserId(null);
        }
        ResponseBody<List<HeroLandStatisticsDetailDP>> result = ResponseBodyWrapper
                .successListWrapper(detailAlls,
                        heroLandStatisticsDetailExtMapper.countStatisticsByRank(qo), qo, HeroLandStatisticsDetailDP.class);
        if (qo.getType().equals(CompetitionEnum.SYNC.getType())) {
            // 同步作业赛时不需要班级排名
            return result;
        }
        try {

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
        // 根据科目来分组
        Map<String, List<HeroLandQuestionTopicListForStatisticDto>> map = topicQuestionForCourseStatistics.stream().filter(d -> StrUtil.isNotBlank(d.getCourseCode())).collect(Collectors.groupingBy(HeroLandQuestionTopicListForStatisticDto::getCourseCode));

        // 去重题组
        List<String> topicIds = topicQuestionForCourseStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getId).map(String::valueOf).distinct().collect(Collectors.toList());

        //  获取某人在当前题目组下的比赛记录
        List<HeroLandCompetitionRecord> heroLandCompetitionRecords = competitionRecordExtMapper.selectByTopicIdsAndInviterId(topicIds, qo.getUserId());

        AtomicReference<Map<String, List<HeroLandCompetitionRecord>>> competitionRecordMap = new AtomicReference<>();
        AtomicReference<Map<String, List<HeroLandQuestionRecordDetail>>> questionRecordMap = new AtomicReference<>();
        if (CollUtil.isNotEmpty(heroLandCompetitionRecords)) {
            competitionRecordMap.set(heroLandCompetitionRecords.stream().collect(Collectors.groupingBy(HeroLandCompetitionRecord::getTopicId)));
            List<String> competitionRecordIds = heroLandCompetitionRecords.stream().map(HeroLandCompetitionRecord::getRecordId).collect(Collectors.toList());
            List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = questionRecordDetailExtMapper.selectByCompetitionRecordId(competitionRecordIds, qo.getUserId());
            if (CollUtil.isNotEmpty(heroLandQuestionRecordDetails)) {
                questionRecordMap.set(heroLandQuestionRecordDetails.stream().collect(Collectors.groupingBy(HeroLandQuestionRecordDetail::getTopicId)));
            }
        }
        List<CompetitionCourseFinishStatisticDP> dps = new ArrayList<>();
        // 循环增加参数
        map.forEach((courseCode, questionTopicStatistics) -> {
            CompetitionCourseFinishStatisticDP dp = new CompetitionCourseFinishStatisticDP();
            HeroLandQuestionTopicListForStatisticDto dto = questionTopicStatistics.get(0);
            dp.setCourseCode(courseCode);
            dp.setTopicIds(questionTopicStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getId).collect(Collectors.toList()));
            dp.setCourseName(dto.getCourseName());
            dp.setClassCode(dto.getClassCode());
            dp.setQuestionNum(questionTopicStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getQuestionNum).reduce(0, Integer::sum));
            dp.setChapterCount(questionTopicStatistics.stream().map(q -> q.getChapterList().size()).reduce(0, Integer::sum));
            dp.setSectionCount(questionTopicStatistics.stream().map(q -> q.getSectionList().size()).reduce(0, Integer::sum));
            Map<Long, HeroLandQuestionTopicListForStatisticDto> statisticDtoMap = questionTopicStatistics.stream().collect(Collectors.toMap(HeroLandQuestionTopicListForStatisticDto::getId, Function.identity(), (o, n) -> n));

            if (MapUtil.isNotEmpty(competitionRecordMap.get())) {

                statisticDtoMap.forEach((topicId, statisticDto) -> {
                    List<HeroLandCompetitionRecord> competitionRecords = competitionRecordMap.get().get(String.valueOf(topicId));
                    if (CollUtil.isNotEmpty(competitionRecords)) {
                        long winCount = competitionRecords.stream().map(HeroLandCompetitionRecord::getResult).filter(Objects::nonNull).filter(c -> Integer.valueOf(0).equals(c)).count();
                        // 胜率
                        BigDecimal winRate = new BigDecimal(winCount).divide(new BigDecimal(competitionRecords.size()), 2, RoundingMode.HALF_UP);
                        dp.setWinRate(winRate);
                    }
                    if (ObjectUtil.isNotNull(questionRecordMap.get()) && CollUtil.isNotEmpty(questionRecordMap.get())) {
                        List<HeroLandQuestionRecordDetail> questionRecordDetails = questionRecordMap.get().get(String.valueOf(topicId));
                        if (CollUtil.isNotEmpty(questionRecordDetails)) {
                            // 完成情况
                            dp.setFinishQuestion(questionRecordDetails.size());
                        }
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
     *
     * @param qo
     * @return
     */
    @Override
    public ResponseBody<List<AnswerQuestionRecordStatisticDP>> getAnswerQuestionRecordStatistic(HeroLandTopicQuestionsPageRequest qo) {


        // 真正要返回的题目
//        if (qo.getTopicIds() == null) {
//            return ResponseBodyWrapper.fail("题目参数为空", "40002");
//        }
        List<HeroLandTopicDto> topics = Lists.newArrayList();
        PageResponse<HeroLandQuestionListForTopicDto> topicQuestions = new PageResponse<>();
        topicQuestions = heroLandQuestionService.getTopicQuestions(qo);


        // 根据topicId 加questionId查出 题目的对战情况
        HeroLandCompetitionRecordQO heroLandQuestionQO = new HeroLandCompetitionRecordQO();
        BeanUtil.copyProperties(qo, heroLandQuestionQO);
        heroLandQuestionQO.setTopicIds(new HashSet<>(qo.getTopicIds()));
        heroLandQuestionQO.setNeedPage(false);
        heroLandQuestionQO.setUserId(qo.getUserId());
        ResponseBody<List<HeroLandCompetitionRecordDP>> questionRecord = heroLandCompetitionRecordService.getCompetitionRecordsAndDetail(heroLandQuestionQO);

        List<HeroLandCompetitionRecordDP> items = questionRecord.getData();
        /**
         * Map<String, HitRuleConfig> configMap = configList.parallelStream().collect(
         *  　　　　　　　　　　　　　　Collectors.groupingBy(HitRuleConfig::getAppId, // 先根据appId分组
         * 　　　　　　　　　　　　　　 Collectors.collectingAndThen(
         * 　　　　　　　　　　　　　　 Collectors.reducing(( c1,  c2) -> c1.getVersionSort() > c2.getVersionSort() ? c1 : c2), Optional::get)));
         */
        Map<String, HeroLandCompetitionRecordDP> collect = items.stream().collect(Collectors.toMap(HeroLandCompetitionRecordDP::getTopicId, Function.identity(), (a, b) -> a.getGmtCreate().getTime() > b.getGmtCreate().getTime() ? a : b));

        ResponseBody<List<AnswerQuestionRecordStatisticDP>> responseBody = new ResponseBody<>();

        List<HeroLandQuestionListForTopicDto> topicQuestionsItems = topicQuestions.getItems();
        //根据topicId和questionId group by
        Map<String, HeroLandCompetitionRecordDP> topic = Maps.newHashMap();
        for (HeroLandCompetitionRecordDP item : collect.values()) {
            List<HeroLandQuestionRecordDetailDP> details = item.getDetails();
            if (details != null){
                for (HeroLandQuestionRecordDetailDP detail : details) {
                    topic.put(item.getTopicId() + detail.getQuestionId(),item);
                }
            }
        }

        for (HeroLandQuestionListForTopicDto v : topicQuestionsItems) {// 题目id，题组名称。对手。知识点、难度。题型、对错、胜负、得分
            HeroLandCompetitionRecordDP record = topic.get(v.getTopicId().toString() + v.getId());

            if (record != null) {
                List<HeroLandQuestionRecordDetailDP> details = record.getDetails();
                if (!CollectionUtils.isEmpty(details)) {
                    for (HeroLandQuestionRecordDetailDP detail : details) {
                        if (detail.getQuestionId().equals(v.getId())) {
                            if (qo.getUserId().equalsIgnoreCase(record.getInviteId())) {
                                if (record.getResult() != null) {
                                    // 如果当前人是邀请者 0负1胜2平局
                                    if (record.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                        v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                                        // 如果是被邀请者胜
                                    } else if (record.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                        v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                                    } else {
                                        v.setResult(record.getResult());
                                    }
                                }
                                v.setOpponent(HeroLevelEnum.getLevelDistance(record.getInviteLevel(), record.getOpponentLevel()));
                            } else {
                                if (record.getResult() != null) {
                                    // 如果当前人是被邀请者 0负1胜2平局
                                    if (record.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                        v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                                        // 如果是被邀请者胜
                                    } else if (record.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                        v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                                    } else {
                                        v.setResult(record.getResult());
                                    }
                                }
                                v.setOpponent(HeroLevelEnum.getLevelDistance(record.getOpponentLevel(), record.getInviteLevel()));
                            }
                            v.setCorrectAnswer(detail.getCorrectAnswer());


                            PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
                            platformSysUserQO.setUserId(record.getOpponentId());

                            if (qo.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                                v.setScore(detail.getScore());
                            } else {
                                v.setScore(detail.getScore());
                            }
                        }
                    }
                }
            }
        }


        List<AnswerQuestionRecordStatisticDP> result = new ArrayList<>();

        List<HeroLandQuestionListForTopicDto> questionsItems = topicQuestions.getItems();
        questionsItems.forEach(v -> {
            AnswerQuestionRecordStatisticDP answerQuestionRecordStatisticDP = new AnswerQuestionRecordStatisticDP();
            answerQuestionRecordStatisticDP.setDiff(v.getDiff());
            List<HerolandKnowledgeSimpleDto> knowledges = v.getKnowledges();
            if (!CollectionUtils.isEmpty(knowledges)) {
                answerQuestionRecordStatisticDP.setKnowledge(knowledges.stream().map(HerolandKnowledgeSimpleDto::getKnowledge).collect(Collectors.toList()));
            }
            answerQuestionRecordStatisticDP.setOpponent(v.getOpponent());
            answerQuestionRecordStatisticDP.setCorrectAnswer(v.getCorrectAnswer());
            answerQuestionRecordStatisticDP.setScore(v.getScore());
            answerQuestionRecordStatisticDP.setResult(v.getResult());
            answerQuestionRecordStatisticDP.setTopicName(v.getTopicName());
            answerQuestionRecordStatisticDP.setType(v.getType());
            if (CompetitionEnum.SYNC.getType().equals(qo.getTopicType())) {
                answerQuestionRecordStatisticDP.setQuestionId(v.getId());
            }
            answerQuestionRecordStatisticDP.setId(v.getId());
            answerQuestionRecordStatisticDP.setTopicId(v.getTopicId());
            answerQuestionRecordStatisticDP.setQuestionTitle(v.getTitle());
            if (v.getKnowledges() != null) {

                answerQuestionRecordStatisticDP.setKnowledge(v.getKnowledges().stream().map(HerolandKnowledgeSimpleDto::getKnowledge).collect(Collectors.toList()));
            }
            result.add(answerQuestionRecordStatisticDP);

        });
        responseBody.setData(result);
        responseBody.setPage(new

                Pagination(qo.getPageIndex(), qo.

                getPageSize(), topicQuestions.

                getTotal()));


        return responseBody;

    }

    private String fetchGroupKey(HeroLandCompetitionRecordDP user) {
        return user.getTopicId() + user.getQuestionId();
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
        List<HeroLandQuestionRecordDetail> questionRecordDetails = questionRecordDetailExtMapper.selectByCompetitionRecordId(Lists.newArrayList(qo.getCompetitionRecordId()), qo.getUserId());
        List<AnswerCompetitionResultDP.AnswerDetail> answerDetails = new ArrayList<>();
        questionRecordDetails.forEach(questionRecord -> {
            AnswerCompetitionResultDP.AnswerDetail answerDetail = new AnswerCompetitionResultDP.AnswerDetail();
            // TODO 题型
//            answerDetail.setDiff(questionRecord.ge);
            answerDetail.setCorrectAnswer(questionRecord.getCorrectAnswer());
            answerDetail.setScore(questionRecord.getScore());
            // TODO 用时
//            answerDetail.setUseTime();
            answerDetails.add(answerDetail);
        });
        dp.setAnswerDetails(answerDetails);
        return ResponseBodyWrapper.successWrapper(dp);
    }

    @Override
    public PageResponse<CourseResultForWDto> courseResultForWQO(CourseResultForWQO qo) {
        PageResponse<CourseResultForWDto> pageResult = new PageResponse<>();
        List<CourseResultForWDto> result = Lists.newArrayList();
        HerolandStatisticsWordExample example = new HerolandStatisticsWordExample();
        HerolandStatisticsWordExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(qo.getUserId()).andSubjectCodeEqualTo(qo.getCourseCode());
        if (Objects.nonNull(qo.getBeginTime())) {
            criteria.andStartTimeLessThanOrEqualTo(qo.getBeginTime());
        }
        if (Objects.nonNull(qo.getEndTime())) {
            criteria.andEndTimeGreaterThanOrEqualTo(qo.getEndTime());
        }
        Page<HerolandStatisticsWord> words = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                () -> herolandStatisticsWordMapper.selectByExample(example));
        if (!CollectionUtils.isEmpty(words.getResult())) {
            result = words.getResult().stream().map(e -> {
                CourseResultForWDto dto = new CourseResultForWDto();
                dto.setEndTime(e.getEndTime());
                dto.setStartTime(e.getStartTime());
                dto.setRank(e.getTotalRank());
                dto.setTotalScore(e.getTotalScore());
                dto.setTotalUseTime(e.getTotalTime());
                dto.setCourseCode(e.getSubjectCode());
                dto.setCourseName(e.getSubjectName());
                return dto;
            }).collect(Collectors.toList());
        }
        pageResult.setItems(result);
        pageResult.setPageSize(words.getPageSize());
        pageResult.setPage(words.getPageNum());
        pageResult.setTotal((int) words.getTotal());
        return pageResult;
    }

    @Override
    public List<AllCourseResultForWDto> allCourseResultForWQO(AllCourseResultForWQO qo) {
        List<String> courseList = herolandStatisticsWordMapper.distinctCourseInTime(qo.getUserId(), qo.getBeginTime(), qo.getEndTime());
        HerolandStatisticsWordExample example = new HerolandStatisticsWordExample();
        HerolandStatisticsWordExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isEmpty(courseList)) {
            return Lists.newArrayList();
        }
        List<AllCourseResultForWDto> list = Lists.newArrayList();
        criteria.andUserIdEqualTo(qo.getUserId()).andSubjectCodeIn(courseList);
        if (Objects.nonNull(qo.getBeginTime())) {
            criteria.andStartTimeLessThanOrEqualTo(qo.getBeginTime());
        }
        if (Objects.nonNull(qo.getEndTime())) {
            criteria.andEndTimeGreaterThanOrEqualTo(qo.getEndTime());
        }
        List<HerolandStatisticsWord> herolandStatisticsWords = herolandStatisticsWordMapper.selectByExample(example);
        Map<String, List<HerolandStatisticsWord>> courseMap = herolandStatisticsWords.stream().collect(Collectors.groupingBy(HerolandStatisticsWord::getSubjectCode));
        for (Map.Entry<String, List<HerolandStatisticsWord>> entry : courseMap.entrySet()) {
            AllCourseResultForWDto dto = new AllCourseResultForWDto();
            dto.setCourseCode(entry.getKey());
            dto.setCourseName(entry.getValue().get(0).getSubjectName());
            dto.setTotalScore(entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalScore).sum());
            dto.setTotalTopics(entry.getValue().stream().map(HerolandStatisticsWord::getTopicId).collect(Collectors.toSet()).size());
            int rank = entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalRank).sum() / dto.getTotalTopics();
            int time = entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalTime).sum() / dto.getTotalTopics();
            double rightRate = entry.getValue().stream().mapToDouble(HerolandStatisticsWord::getAnswerRightRate).sum() / dto.getTotalTopics();
            double winRate = entry.getValue().stream().mapToDouble(HerolandStatisticsWord::getWinRate).sum() / dto.getTotalTopics();
            dto.setRank(rank);
            dto.setAverageTime(time);
            dto.setRightRate(rightRate);
            dto.setWinRate(winRate);
            dto.setAverageScore(dto.getTotalScore() / dto.getTotalTopics());
            list.add(dto);
        }
        return list;
    }

    @Override
    public PageResponse<CourseResultForUserDto> getAllCourseResultForUser(CourseResultForUserQO qo) {
        PageResponse<CourseResultForUserDto> pageResult = new PageResponse<>();
        List<CourseResultForUserDto> list = Lists.newArrayList();
        HerolandStatisticsWordExample example = new HerolandStatisticsWordExample();
        HerolandStatisticsWordExample.Criteria criteria = example.createCriteria();
        criteria.andSubjectCodeEqualTo(qo.getCourseCode());
        if (Objects.nonNull(qo.getBeginTime())) {
            criteria.andStartTimeLessThanOrEqualTo(qo.getBeginTime());
        }
        if (Objects.nonNull(qo.getEndTime())) {
            criteria.andEndTimeGreaterThanOrEqualTo(qo.getEndTime());
        }
        List<HerolandStatisticsWord> words = herolandStatisticsWordMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(words)) {
            Map<String, List<HerolandStatisticsWord>> userMap = words.stream().collect(Collectors.groupingBy(HerolandStatisticsWord::getUserId));
            for (Map.Entry<String, List<HerolandStatisticsWord>> entry : userMap.entrySet()) {
                CourseResultForUserDto dto = new CourseResultForUserDto();
                dto.setCourseCode(qo.getCourseCode());
                dto.setCourseName(entry.getValue().get(0).getSubjectName());
                dto.setTotalScore(entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalScore).sum());
                dto.setTotalTopics(entry.getValue().stream().map(HerolandStatisticsWord::getTopicId).collect(Collectors.toSet()).size());
                int rank = entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalRank).sum() / dto.getTotalTopics();
                int time = entry.getValue().stream().mapToInt(HerolandStatisticsWord::getTotalTime).sum() / dto.getTotalTopics();
                double rightRate = entry.getValue().stream().mapToDouble(HerolandStatisticsWord::getAnswerRightRate).sum() / dto.getTotalTopics();
                double winRate = entry.getValue().stream().mapToDouble(HerolandStatisticsWord::getWinRate).sum() / dto.getTotalTopics();
                dto.setRank(rank);
                dto.setAverageTime(time);
                dto.setRightRate(rightRate);
                dto.setWinRate(winRate);
                dto.setAverageScore(dto.getTotalScore() / dto.getTotalTopics());
                dto.setUserId(entry.getKey());
                dto.setUserId(entry.getValue().get(0).getUserName());
                dto.setSchoolCode(entry.getValue().get(0).getSchoolCode());
                dto.setSchoolName(entry.getValue().get(0).getSchoolName());
                list.add(dto);
            }
        }
        if (qo.getStartRow() >= list.size()) {
            pageResult.setItems(Lists.newArrayList());
        } else if ((qo.getStartRow() + qo.getPageSize() - 1) >= list.size()) {
            pageResult.setItems(list.subList(qo.getStartRow(), (list.size() - 1)));
        } else {
            pageResult.setItems(list.subList(qo.getStartRow(), (qo.getStartRow() + qo.getPageSize() - 1)));
        }
        pageResult.setTotal(list.size());
        pageResult.setPageSize(qo.getPageSize());
        pageResult.setPage(qo.getPageIndex());
        return pageResult;
    }

    @Override
    public ResponseBody<List<WorldStatisticResultDto>> worldStatisticResult(WorldStatisticQO qo) {
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(qo.getTopicId());
        if (heroLandTopicGroup == null || heroLandTopicGroup.getIsDeleted()) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_QUERY_PARAM.getErrorMessage());
        }
        if (heroLandTopicGroup.getEndTime().after(new Date())) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.STATISTIC_NOT.getErrorMessage());
        }

        HerolandStatisticsLogExample logExample = new HerolandStatisticsLogExample();
        HerolandStatisticsLogExample.Criteria logExampleCriteria = logExample.createCriteria();
        logExampleCriteria.andTopicIdEqualTo(qo.getTopicId()).andIsFinishedEqualTo(true).andIsDeletedEqualTo(false);
        List<HerolandStatisticsLog> herolandStatisticsLogs = herolandStatisticsLogMapper.selectByExample(logExample);
        //如果标记为已完成，则直接从统计库理捞数据;
        long count = 0L;
        List<HerolandStatisticsWord> herolandStatisticsWords = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(herolandStatisticsLogs)) {
            HerolandStatisticsWordExample example = new HerolandStatisticsWordExample();
            HerolandStatisticsWordExample.Criteria criteria = example.createCriteria();
            criteria.andTopicIdEqualTo(qo.getTopicId()).andIsDeletedEqualTo(false).andStatisticTypeEqualTo(TopicJoinConstant.statisic_type_total);
            if (qo.getNeedPage()) {
                example.setOrderByClause("total_score desc limit " + qo.getStartRow() + "," + qo.getPageSize());
            } else {
                example.setOrderByClause("total_score desc");
            }
            herolandStatisticsWords = herolandStatisticsWordMapper.selectByExample(example);
            count = herolandStatisticsWordMapper.countByExample(example);
            ResponseBody<List<WorldStatisticResultDto>> listResponseBody = ResponseBodyWrapper.successListWrapper(herolandStatisticsWords, count, qo, WorldStatisticResultDto.class);
            if (listResponseBody.isSuccess() && !CollectionUtils.isEmpty(listResponseBody.getData())) {
                listResponseBody.getData().forEach(e -> {
                    e.setRegisterbeginTime(heroLandTopicGroup.getRegisterBeginTime());
                    e.setRegisterEndTime(heroLandTopicGroup.getRegisterEndTime());
                    e.setCountLimit(heroLandTopicGroup.getCountLimit());
                    e.setRegisterCount(heroLandTopicGroup.getRegisterCount());
                    e.setTopicName(heroLandTopicGroup.getTopicName());
                    //构造按总分的排名
                    e.setTotalRank(qo.getStartRow() + listResponseBody.getData().indexOf(e) + 1);
                });
            }
        } else {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.STATISTIC_DOING.getErrorMessage());

        }
        return ResponseBodyWrapper.successListWrapper(herolandStatisticsWords, count, qo, WorldStatisticResultDto.class);
    }

    @Override
    public Boolean deleteHistoryStatisticsTotalAndDetailByQO(HeroLandStatisticsTotalQO qo) {
        HeroLandStatisticsTotal heroLandStatisticsTotal = new HeroLandStatisticsTotal();
        heroLandStatisticsTotal.setHistory(true);
        HeroLandStatisticsTotalExample heroLandStatisticsTotalExample = new HeroLandStatisticsTotalExample();
        HeroLandStatisticsTotalExample.Criteria criteria = heroLandStatisticsTotalExample.createCriteria();
        criteria.andIdIsNotNull();
        heroLandStatisticsTotalExtMapper.deleteByExample(heroLandStatisticsTotalExample);

        HeroLandStatisticsDetail heroLandStatisticsDetail = new HeroLandStatisticsDetail();
        heroLandStatisticsDetail.setHistory(true);
        HeroLandStatisticsDetailExample heroLandStatisticsDetailExample = new HeroLandStatisticsDetailExample();
        HeroLandStatisticsDetailExample.Criteria criteria1 = heroLandStatisticsDetailExample.createCriteria();
        criteria1.andIdIsNotNull();
        heroLandStatisticsDetailExtMapper.deleteByExample(heroLandStatisticsDetailExample);
        return true;
    }
}
