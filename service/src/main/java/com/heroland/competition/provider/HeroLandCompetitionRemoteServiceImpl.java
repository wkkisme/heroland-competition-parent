package com.heroland.competition.provider;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandCompetitionRemoteService;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
@DubboService
public class HeroLandCompetitionRemoteServiceImpl implements HeroLandCompetitionRemoteService {
    @Override
    public Integer getType() {
        return null;
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {
        return null;
    }
}
