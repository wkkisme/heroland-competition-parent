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
import com.heroland.competition.common.enums.*;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.HeroLevelUtils;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsTotalExtMapper;
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
        if (CompetitionEnum.SYNC.getType().equals(qo.getType())) {
            topicQuestions = heroLandQuestionService.getTopicQuestions(qo);
            if (CollectionUtils.isEmpty(topicQuestions.getItems())) {
                return ResponseBodyWrapper.success();
            }
        }  else if (!CompetitionEnum.SCHOOL.getType().equals(qo.getType())){
            topics = heroLandQuestionService.getTopics(qo);
            if (CollectionUtils.isEmpty(topics)) {
                return ResponseBodyWrapper.success();
            }
            qo.setTopicIds(topics.stream().map(HeroLandTopicDto :: getTopicId).collect(Collectors.toList()));
        }

        // 根据topicId 加questionId查出 题目的对战情况
        HeroLandCompetitionRecordQO heroLandQuestionQO = new HeroLandCompetitionRecordQO();
        BeanUtil.copyProperties(qo,heroLandQuestionQO);
        heroLandQuestionQO.setTopicIds(new HashSet<>(qo.getTopicIds()));
        heroLandQuestionQO.setNeedPage(false);
        heroLandQuestionQO.setUserId(qo.getUserId());
        ResponseBody<List<HeroLandCompetitionRecordDP>> questionRecord;
        if (CompetitionEnum.SYNC.getType().equals(qo.getType())) {
            questionRecord = heroLandCompetitionRecordService.getCompetitionRecordsAndDetail(heroLandQuestionQO);
        } else {
            questionRecord = heroLandCompetitionRecordService.getCompetitionRecords(heroLandQuestionQO);
        }

        List<HeroLandCompetitionRecordDP> items = questionRecord.getData();
        ResponseBody<List<AnswerQuestionRecordStatisticDP>> responseBody = new ResponseBody<>();

        List<HeroLandQuestionListForTopicDto> topicQuestionsItems = topicQuestions.getItems();
        if (!CollectionUtils.isEmpty(questionRecord.getData()) && CompetitionEnum.SYNC.getType().equals(qo.getType())) {
            //根据topicId和questionId group by
            Map<String, List<HeroLandCompetitionRecordDP>> topic = items.stream().collect(Collectors.groupingBy(HeroLandCompetitionStatisticsServiceImpl::fetchGroupKey));

            HeroLandTopicQuestionsPageRequest finalQo = qo;
            topicQuestionsItems.forEach(v -> {
                // 题目id，题组名称。对手。知识点、难度。题型、对错、胜负、得分
                List<HeroLandCompetitionRecordDP> heroLandCompetitionRecordDPS = topic.get(v.getTopicId().toString() + v.getId());

                if (heroLandCompetitionRecordDPS != null) {
                    // 因为一个人下的topicId会有多场比赛 取最新的展示
                    List<HeroLandCompetitionRecordDP> sorted = heroLandCompetitionRecordDPS.stream().sorted(Comparator.comparing(HeroLandCompetitionRecordDP::getGmtCreate).reversed()).collect(Collectors.toList());
                    HeroLandCompetitionRecordDP recordDP = sorted.get(0);
                    // 如果questionid相等  同步作业赛
                    List<HeroLandQuestionRecordDetailDP> details = recordDP.getDetails();
                    if (!CollectionUtils.isEmpty(details)) {
                        details.forEach(detail -> {
                            if (detail.getQuestionId().equals(v.getId()) && detail.getQuestionId().equals(recordDP.getQuestionId())) {
                                if (finalQo.getUserId().equalsIgnoreCase(recordDP.getInviteId())) {
                                    if (recordDP.getResult() != null) {
                                        // 如果当前人是邀请者 0负1胜2平局
                                        if (recordDP.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                            v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                                            // 如果是被邀请者胜
                                        } else if (recordDP.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                            v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                                        } else {
                                            v.setResult(recordDP.getResult());
                                        }
                                    }
                                    v.setOpponent(HeroLevelEnum.getLevelDistance(recordDP.getInviteLevel(), recordDP.getOpponentLevel()));
                                } else {
                                    if (recordDP.getResult() != null) {
                                        // 如果当前人是被邀请者 0负1胜2平局
                                        if (recordDP.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                            v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                                            // 如果是被邀请者胜
                                        } else if (recordDP.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                            v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                                        } else {
                                            v.setResult(recordDP.getResult());
                                        }
                                    }
                                    v.setOpponent(HeroLevelEnum.getLevelDistance(recordDP.getOpponentLevel(), recordDP.getInviteLevel()));
                                }
                                v.setCorrectAnswer(detail.getCorrectAnswer());


                                PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
                                platformSysUserQO.setUserId(recordDP.getOpponentId());

                                if (finalQo.getUserId().equalsIgnoreCase(recordDP.getOpponentId())) {
                                    v.setScore(recordDP.getOpponentScore());
                                } else {
                                    v.setScore(recordDP.getInviteScore());
                                }
                            }
                        });
                    }
                }
            });


        } else if (!CollectionUtils.isEmpty(questionRecord.getData())) {
            //根据topicId和questionId group by
            Map<String, List<HeroLandCompetitionRecordDP>> topic = items.stream().collect(Collectors.groupingBy(HeroLandCompetitionRecordDP::getTopicId));
            if (CompetitionEnum.SCHOOL.getType().equals(qo.getType())) {
                HeroLandTopicQuestionsPageRequest heroLandTopicQuestionsPageRequest = new HeroLandTopicQuestionsPageRequest();
                heroLandTopicQuestionsPageRequest.setTopicIds(items.parallelStream().map(HeroLandCompetitionRecordDP::getTopicId).map(Long::valueOf).collect(Collectors.toList()));
                topics = heroLandQuestionService.getTopics(heroLandTopicQuestionsPageRequest);
            }
            topics.forEach(v -> {
                // 题目id，题组名称。对手。知识点、难度。题型、对错、胜负、得分
                List<HeroLandCompetitionRecordDP> heroLandCompetitionRecordDPS = topic.get(v.getId().toString());

                if (heroLandCompetitionRecordDPS != null) {
                    // 因为一个人下的topicId只会有一场比赛 取出来即可
                    HeroLandCompetitionRecordDP recordDP = heroLandCompetitionRecordDPS.get(0);
                    if (qo.getUserId().equalsIgnoreCase(recordDP.getInviteId())) {

                        if (recordDP.getResult() != null) {
                            // 如果当前人是邀请者 0负1胜2平局
                            if (recordDP.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                                // 如果是被邀请者胜
                            } else if (recordDP.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                            } else {
                                v.setResult(recordDP.getResult());
                            }
                        }
                        v.setOpponent(HeroLevelEnum.getLevelDistance(recordDP.getInviteLevel(), recordDP.getOpponentLevel()));
                    } else {
                        if (recordDP.getResult() != null) {
                            // 如果当前人是被邀请者 0负1胜2平局
                            if (recordDP.getResult().equals(CompetitionResultEnum.INVITE_WIN.getResult())) {
                                v.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                                // 如果是被邀请者胜
                            } else if (recordDP.getResult().equals(CompetitionResultEnum.BE_INVITE_WIN.getResult())) {
                                v.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                            } else {
                                v.setResult(recordDP.getResult());
                            }
                        }
                        v.setOpponent(HeroLevelEnum.getLevelDistance(recordDP.getOpponentLevel(), recordDP.getInviteLevel()));
                    }

                    PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
                    platformSysUserQO.setUserId(recordDP.getOpponentId());

                    if (qo.getUserId().equalsIgnoreCase(recordDP.getOpponentId())) {
                        v.setScore(recordDP.getOpponentScore());
                    } else {
                        v.setScore(recordDP.getInviteScore());
                    }


                }
            });


        }
        List<AnswerQuestionRecordStatisticDP> result = new ArrayList<>();
        if (CompetitionEnum.SYNC.getType().equals(qo.getType())) {

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
                answerQuestionRecordStatisticDP.setQuestionId(v.getId());
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
        } else {
            topics.forEach(v -> {

                AnswerQuestionRecordStatisticDP answerQuestionRecordStatisticDP = new AnswerQuestionRecordStatisticDP();
                if (StringUtils.isNotBlank(v.getLevelCode())) {
                    answerQuestionRecordStatisticDP.setDiff(Integer.valueOf(v.getLevelCode()));
                }
                answerQuestionRecordStatisticDP.setOpponent(v.getOpponent());
                answerQuestionRecordStatisticDP.setCorrectAnswer(v.getCorrectAnswer());
                answerQuestionRecordStatisticDP.setScore(v.getScore());
                answerQuestionRecordStatisticDP.setResult(v.getResult());
                answerQuestionRecordStatisticDP.setTopicName(v.getTopicName());
                answerQuestionRecordStatisticDP.setType(v.getDiff());
                answerQuestionRecordStatisticDP.setId(v.getId());
                answerQuestionRecordStatisticDP.setTopicId(v.getId());
                result.add(answerQuestionRecordStatisticDP);
            });
            responseBody.setData(result);
            responseBody.setPage(new
                    Pagination(qo.getPageIndex(), qo.

                    getPageSize(), topics.size()));
        }


        return responseBody;
    }

    private static String fetchGroupKey(HeroLandCompetitionRecordDP user) {
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
}
