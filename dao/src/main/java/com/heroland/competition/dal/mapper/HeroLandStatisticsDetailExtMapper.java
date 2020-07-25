package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailAll;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;

import java.util.List;

public interface HeroLandStatisticsDetailExtMapper extends HeroLandStatisticsDetailMapper{

    List<HeroLandStatisticsDetailAll> selectStatisticsByRank(HeroLandStatisticsTotalQO qo);

    Long countStatisticsByRank(HeroLandStatisticsTotalQO qo);

}