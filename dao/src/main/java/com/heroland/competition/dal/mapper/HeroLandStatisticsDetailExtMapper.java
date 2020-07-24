package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsDetail;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailAll;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailExample;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandStatisticsDetailExtMapper extends HeroLandStatisticsDetailMapper{

    List<HeroLandStatisticsDetailAll> selectStatisticsByRank(HeroLandStatisticsTotalQO qo);

    Long countStatisticsByRank(HeroLandStatisticsTotalQO qo);

}