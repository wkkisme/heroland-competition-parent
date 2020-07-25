package com.heroland.competition.api;

import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;

/**
 * 计分器，根据不同方式来计算分
 * @author wangkai
 */
public interface HeroLandCalculatorRemoteService {


    /**
     * 得分
     * @param dp
     * @param userId
     * @return
     */
    HeroLandCalculatorResultDP calculate(HeroLandCompetitionRecordDP dp, String userId);
}
