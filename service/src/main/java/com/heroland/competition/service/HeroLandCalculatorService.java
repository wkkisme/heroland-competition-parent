package com.heroland.competition.service;

import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;

/**
 * 计分器，根据不同方式来计算分
 * @author wangkai
 */
public interface HeroLandCalculatorService {


    /**
     * 得分
     * @param dp
     * @return
     */
    HeroLandCalculatorResultDP calculate(HeroLandCalculatorResultDP dp);
}
