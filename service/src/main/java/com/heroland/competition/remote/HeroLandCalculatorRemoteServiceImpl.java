package com.heroland.competition.remote;

import com.heroland.competition.api.HeroLandCalculatorRemoteService;
import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.service.HeroLandCalculatorService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
@DubboService(version = "1.0.0")
@AllArgsConstructor
public class HeroLandCalculatorRemoteServiceImpl implements HeroLandCalculatorRemoteService {

    private final HeroLandCalculatorService heroLandCalculatorService;

    @Override
    public HeroLandCalculatorResultDP calculate(HeroLandCompetitionRecordDP dp) {
        return heroLandCalculatorService.calculate(dp);
    }
}
