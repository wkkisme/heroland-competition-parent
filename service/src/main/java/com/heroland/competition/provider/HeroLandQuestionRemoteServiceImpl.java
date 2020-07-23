package com.heroland.competition.provider;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandQuestionRemoteService;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
@DubboService
public class HeroLandQuestionRemoteServiceImpl implements HeroLandQuestionRemoteService {
    @Override
    public ResponseBody<Boolean> addQuestion(HeroLandQuestionDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> deleteQuestion(HeroLandQuestionDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> addTopicQuestions(HeroLandTopicGroupDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopicQuestions(HeroLandQuestionQO qo) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandQuestionDP>> getQuestion(HeroLandQuestionQO qo) {
        return null;
    }
}
