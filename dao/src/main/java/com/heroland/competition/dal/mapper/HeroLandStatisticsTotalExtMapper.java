package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsDetail;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotalAll;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotalExample;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandStatisticsTotalExtMapper  extends HeroLandStatisticsTotalMapper{

    List<HeroLandStatisticsTotalAll> selectStatisticsByRank(HeroLandStatisticsTotalQO qo);

    Long countStatisticsByRank(HeroLandStatisticsTotalQO qo);

}