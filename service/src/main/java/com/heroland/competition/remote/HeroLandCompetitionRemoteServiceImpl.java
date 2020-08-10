package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandCompetitionRemoteService;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.service.HeroLandCompetitionService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
//@Service(version = "1.0.0")
@AllArgsConstructor
public class HeroLandCompetitionRemoteServiceImpl implements HeroLandCompetitionRemoteService {

    private final HeroLandCompetitionService competitionService;

    @Override
    public Integer getType() {
        return competitionService.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {
        return competitionService.doAnswer(dp);
    }
}
