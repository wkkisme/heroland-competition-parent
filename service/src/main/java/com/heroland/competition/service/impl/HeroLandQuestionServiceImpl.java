package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionExtMapper;
import com.heroland.competition.dal.pojo.HeroLandQuestion;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mac
 */
@Service
public class HeroLandQuestionServiceImpl implements HeroLandQuestionService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandTopicGroupServiceImpl.class);

    @Resource
    private HeroLandQuestionExtMapper heroLandQuestionExtMapper;

//    private


    @Override
    public ResponseBody<Boolean> addQuestion(HeroLandQuestionDP dp) {
        try {
            heroLandQuestionExtMapper.insert(BeanUtil.insertConversion(dp.addCheck(),new HeroLandQuestion()));
        } catch (Exception e) {
            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> deleteQuestion(HeroLandQuestionDP dp) {
        try {
            heroLandQuestionExtMapper.updateByPrimaryKey(BeanUtil.updateConversion(dp.deleteCheck(),new HeroLandQuestion()));
        } catch (Exception e) {
            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> addTopicQuestions(HeroLandQuestionDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandQuestionDP>> getQuestionQuestions(HeroLandQuestionQO qo) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandQuestionDP>> getQuestion(HeroLandQuestionQO qo) {
        return null;
    }
}
