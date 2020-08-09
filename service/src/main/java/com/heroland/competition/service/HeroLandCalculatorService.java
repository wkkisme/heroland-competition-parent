package com.heroland.competition.service;

import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;

/**
 * 计分器，根据不同方式来计算分
 * @author wangkai
 */
public interface HeroLandCalculatorService {


    /**
     * 类型，比赛类型
     * @return
     */
    Integer getType();
    /**
     * 得分
     * @param dp
     * @return
     */
    HeroLandCompetitionRecordDP calculate(HeroLandCompetitionRecordDP dp);



}
