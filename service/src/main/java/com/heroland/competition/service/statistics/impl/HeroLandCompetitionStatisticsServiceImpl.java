package com.heroland.competition.service.statistics.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.OrderByEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandStatisticsDetailMapper;
import com.heroland.competition.dal.mapper.HeroLandStatisticsTotalMapper;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetail;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailExample;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotalExample;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计
 *
 * @author wangkai
 */
@Service
@Slf4j
public class HeroLandCompetitionStatisticsServiceImpl implements HeroLandCompetitionStatisticsService {

    @Resource
    private HeroLandStatisticsTotalMapper heroLandStatisticsTotalMapper;

    @Resource
    private HeroLandStatisticsDetailMapper heroLandStatisticsDetailMapper;

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
                heroLandStatisticsTotalMapper.insert(heroLandStatisticsTotal);
            }
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<List<HeroLandStatisticsTotalDP>> getCompetitionsTatal(HeroLandStatisticsTotalQO qo) {
        HeroLandStatisticsTotalExample heroLandStatisticsTotalExample = new HeroLandStatisticsTotalExample();
        if (qo.getOrderByFiled() != null) {
            heroLandStatisticsTotalExample.setOrderByClause(qo.getOrderByFiled().getFiled());
        } else {
            heroLandStatisticsTotalExample.setOrderByClause(OrderByEnum.TOTAL_SCORE_DESC.getFiled());
        }
        HeroLandStatisticsTotalExample.Criteria criteria = heroLandStatisticsTotalExample.createCriteria();


        MybatisCriteriaConditionUtil.createExample(criteria, qo);
        try {
            return ResponseBodyWrapper
                    .successListWrapper(heroLandStatisticsTotalMapper.selectByExample(heroLandStatisticsTotalExample),
                            heroLandStatisticsTotalMapper.countByExample(heroLandStatisticsTotalExample), qo, HeroLandStatisticsTotalDP.class);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return null;
    }

    @Override
    public ResponseBody<Boolean> saveStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> dp) {
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : dp) {
            heroLandStatisticsTotalDP.addTotalAndDetailCheck();
        }
        saveStatisticsTotal(dp);
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : dp) {
            for (HeroLandStatisticsDetailDP detail : heroLandStatisticsTotalDP.getDetails()) {
                try {
                    heroLandStatisticsDetailMapper.insert(BeanUtil.insertConversion(detail, new HeroLandStatisticsDetail()));
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
                heroLandStatisticsTotalMapper.updateByExample(BeanUtil.updateConversion(heroLandStatisticsTotalDP, new HeroLandStatisticsTotal()), totalExample);
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
        heroLandStatisticsTotalMapper.updateByExampleSelective(heroLandStatisticsTotal, heroLandStatisticsTotalExample);

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
                    heroLandStatisticsDetailMapper.updateByExample(BeanUtil.updateConversion(heroLandStatisticsTotalDP, new HeroLandStatisticsDetail()), heroLandStatisticsDetailExample);
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
        HeroLandStatisticsDetailExample heroLandStatisticsDetailExample = new HeroLandStatisticsDetailExample();
        MybatisCriteriaConditionUtil.createExample(heroLandStatisticsDetailExample.createCriteria(), qo);

        try {
            return ResponseBodyWrapper
                    .successListWrapper(heroLandStatisticsDetailMapper.selectByExample(heroLandStatisticsDetailExample),
                            heroLandStatisticsDetailMapper.countByExample(heroLandStatisticsDetailExample), qo, HeroLandStatisticsDetailDP.class);
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
}
