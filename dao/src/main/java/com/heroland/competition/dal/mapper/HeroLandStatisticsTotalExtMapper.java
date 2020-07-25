package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsTotalAll;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;

import java.util.List;

public interface HeroLandStatisticsTotalExtMapper  extends HeroLandStatisticsTotalMapper{

    List<HeroLandStatisticsTotalAll> selectStatisticsByRank(HeroLandStatisticsTotalQO qo);

    Long countStatisticsByRank(HeroLandStatisticsTotalQO qo);

}