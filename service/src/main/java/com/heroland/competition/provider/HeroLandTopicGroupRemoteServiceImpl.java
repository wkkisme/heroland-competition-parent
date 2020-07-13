package com.heroland.competition.provider;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandTopicGroupRemoteService;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
public class HeroLandTopicGroupRemoteServiceImpl implements HeroLandTopicGroupRemoteService {
    @Override
    public ResponseBody<Boolean> addTopic(HeroLandTopicGroupDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> deleteTopic(HeroLandTopicGroupDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopic(HeroLandTopicGroupQO qo) {
        return null;
    }
}
