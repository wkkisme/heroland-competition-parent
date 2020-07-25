package com.heroland.competition.service.statistics.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.OrderByEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsDetailExtMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsTotalExtMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.dp.SyncCompetitionCourseFinishStatisticDP;
import com.heroland.competition.domain.dto.HeroLandQuestionTopicListForStatisticDto;
import com.heroland.competition.domain.qo.CourseFinishStatisticQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    private HeroLandStatisticsTotalExtMapper heroLandStatisticsTotalExtMapper;

    @Resource
    private HeroLandStatisticsDetailExtMapper heroLandStatisticsDetailExtMapper;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

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

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getCompetitionsTatal(HeroLandStatisticsTotalQO qo) {
        if (qo.getOrderByField() != null) {
            qo.setOrderField(qo.getOrderByField().getOrderByFiled());
            qo.setRankField(qo.getOrderByField().getFiled());
        } else {
            qo.setOrderField(OrderByEnum.TOTAL_SCORE_DESC.getOrderByFiled());
            qo.setRankField(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
        }

        try {
            return ResponseBodyWrapper
                    .successListWrapper(heroLandStatisticsTotalExtMapper.selectStatisticsByRank(qo),
                            heroLandStatisticsTotalExtMapper.countStatisticsByRank(qo), qo, HeroLandStatisticsTotalDP.class);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return null;
    }

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
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getSyncCompetitions(HeroLandStatisticsTotalQO qo) {
        qo.setType(CompetitionEnum.SYNC.getType());
        qo.setHistory(false);
        return getCompetitionsTatal(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        AssertUtils.notBlank(qo.getUserId());
        qo.setHistory(false);
        qo.setType(CompetitionEnum.SYNC.getType());
        return getCompetitionsDetail(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        if (qo.getOrderByField() != null) {
            qo.setOrderField(qo.getOrderByField().getOrderByFiled());
            qo.setRankField(qo.getOrderByField().getFiled());
        } else {
            qo.setOrderField(OrderByEnum.TOTAL_SCORE_DESC.getOrderByFiled());
            qo.setRankField(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
        }

        try {
            return ResponseBodyWrapper
                    .successListWrapper(heroLandStatisticsDetailExtMapper.selectStatisticsByRank(qo),
                            heroLandStatisticsDetailExtMapper.countStatisticsByRank(qo), qo, HeroLandStatisticsDetailDP.class);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getWinterVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        AssertUtils.notBlank(qo.getUserId());
        qo.setHistory(false);
        qo.setType(CompetitionEnum.WINTER.getType());
        return getCompetitionsDetail(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getWinterVacationCompetitions(HeroLandStatisticsTotalQO qo) {
        qo.setType(CompetitionEnum.WINTER.getType());
        qo.setHistory(false);
        return getCompetitionsTatal(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getSummerVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        AssertUtils.notBlank(qo.getUserId());
        qo.setHistory(false);
        qo.setType(CompetitionEnum.SUMMER.getType());
        return getCompetitionsDetail(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getSummerVacationCompetitions(HeroLandStatisticsTotalQO qo) {
        qo.setType(CompetitionEnum.SUMMER.getType());
        qo.setHistory(false);
        return getCompetitionsTatal(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getWorldCompetitionsDetail(HeroLandStatisticsTotalQO qo) {
        AssertUtils.notBlank(qo.getUserId());
        qo.setHistory(false);
        qo.setType(CompetitionEnum.WORLD.getType());
        return getCompetitionsDetail(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getWorldCompetitions(HeroLandStatisticsTotalQO qo) {
        qo.setType(CompetitionEnum.WORLD.getType());
        qo.setHistory(false);
        return getCompetitionsTatal(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getSchoolsCompetitions(HeroLandStatisticsTotalQO qo) {
        qo.setType(CompetitionEnum.SCHOOL.getType());
        qo.setHistory(false);
        return getCompetitionsTatal(qo);
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsDetailDP>> getSchoolCompetitions(HeroLandStatisticsTotalQO qo) {
        AssertUtils.notBlank(qo.getUserId());
        AssertUtils.notBlank(qo.getUserId());
        qo.setHistory(false);
        qo.setType(CompetitionEnum.SCHOOL.getType());
        return getCompetitionsDetail(qo);
    }

    @Override
    public ResponseBody<List<SyncCompetitionCourseFinishStatisticDP>> getCourseFinishStatistic(CourseFinishStatisticQO qo) {

        HeroLandTopicQuestionForCourseRequest request = new HeroLandTopicQuestionForCourseRequest();
        BeanUtil.copyProperties(qo, request);
        List<HeroLandQuestionTopicListForStatisticDto> topicQuestionForCourseStatistics = heroLandQuestionService.getTopicQuesitionForCourseStatistics(request);
        if (CollUtil.isEmpty(topicQuestionForCourseStatistics)) {
            return ResponseBodyWrapper.success();
        }
        Map<String, List<HeroLandQuestionTopicListForStatisticDto>> map = topicQuestionForCourseStatistics.stream().collect(Collectors.groupingBy(HeroLandQuestionTopicListForStatisticDto::getCourseName));
        List<Long> topicIds = topicQuestionForCourseStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getId).distinct().collect(Collectors.toList());
        List<HeroLandCompetitionRecord> heroLandCompetitionRecords = competitionRecordExtMapper.selectByTopicIdsAndInviterId(topicIds, qo.getUserId());
        if (CollUtil.isEmpty(heroLandCompetitionRecords)) {
            return ResponseBodyWrapper.success();
        }
        Map<String, List<HeroLandCompetitionRecord>> competitionRecordMap = heroLandCompetitionRecords.stream().collect(Collectors.groupingBy(HeroLandCompetitionRecord::getTopicId));
        List<HeroLandQuestionRecordDetailDP> heroLandQuestionRecordDetails = questionRecordDetailExtMapper.selectByTopicIdsAndUserId(topicIds, qo.getUserId());
        AtomicReference<Map<Long, List<HeroLandQuestionRecordDetailDP>>> questionRecordMap = new AtomicReference<>();
        if (CollUtil.isNotEmpty(heroLandQuestionRecordDetails)) {
            questionRecordMap.set(heroLandQuestionRecordDetails.stream().collect(Collectors.groupingBy(e -> Long.valueOf(e.getTopicId()))));
        }
        List<SyncCompetitionCourseFinishStatisticDP> dps = new ArrayList<>();
        map.forEach((courseName, questionTopicStatistics) -> {
            SyncCompetitionCourseFinishStatisticDP dp = new SyncCompetitionCourseFinishStatisticDP();
            HeroLandQuestionTopicListForStatisticDto dto = questionTopicStatistics.get(0);
            dp.setCourseCode(dto.getCourseCode());
            dp.setCourseName(courseName);
            dp.setClassCode(dto.getClassCode());
            dp.setQuestionNum(questionTopicStatistics.stream().map(HeroLandQuestionTopicListForStatisticDto::getQuestionNum).reduce(0, Integer::sum));
            dp.setChapterCount(questionTopicStatistics.stream().map(q -> q.getChapterList().size()).reduce(0, Integer::sum));
            dp.setSectionCount(questionTopicStatistics.stream().map(q -> q.getSectionList().size()).reduce(0, Integer::sum));
            Map<Long, HeroLandQuestionTopicListForStatisticDto> statisticDtoMap = questionTopicStatistics.stream().collect(Collectors.toMap(HeroLandQuestionTopicListForStatisticDto::getId, Function.identity(), (o, n) -> n));
            statisticDtoMap.forEach((topicId, statisticDto) -> {
                List<HeroLandCompetitionRecord> competitionRecords = competitionRecordMap.get(String.valueOf(topicId));
                long winCount = competitionRecords.stream().map(HeroLandCompetitionRecord::getResult).filter(c -> c.equals(0)).count();
                // 胜率
                BigDecimal winRate = new BigDecimal(winCount).divide(new BigDecimal(competitionRecords.size()), 2, RoundingMode.HALF_UP);
                dp.setWinRate(winRate);
                if (ObjectUtil.isNotNull(questionRecordMap.get()) && CollUtil.isNotEmpty(questionRecordMap.get())) {
                    List<HeroLandQuestionRecordDetailDP> questionRecordDetails = questionRecordMap.get().get(topicId);
                    // 完成情况
                    dp.setFinishQuestion(questionRecordDetails.size());
                }
                // TODO 完成多少节 不知道怎么统计
            });

            dps.add(dp);
        });
        return ResponseBodyWrapper.successWrapper(dps);
    }
}
