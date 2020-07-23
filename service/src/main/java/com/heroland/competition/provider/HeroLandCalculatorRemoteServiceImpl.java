package com.heroland.competition.provider;

import com.heroland.competition.api.HeroLandCalculatorRemoteService;
import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
@DubboService
public class HeroLandCalculatorRemoteServiceImpl implements HeroLandCalculatorRemoteService {
    @Override
    public HeroLandCalculatorResultDP calculate(HeroLandCompetitionRecordDP dp) {
        return null;
    }
}
