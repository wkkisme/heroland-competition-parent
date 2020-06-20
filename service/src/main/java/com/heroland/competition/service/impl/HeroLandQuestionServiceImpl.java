package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.dal.mapper.HeroLandQuestionExtMapper;
import com.heroland.competition.dal.mapper.HeroLandTopicQuestionExtMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
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

    @Resource
    private HeroLandTopicQuestionExtMapper heroLandTopicQuestionExtMapper;


    @Override
    public ResponseBody<Boolean> addQuestion(HeroLandQuestionDP dp) {
        try {
            heroLandQuestionExtMapper.insert(BeanUtil.insertConversion(dp.addCheck(), new HeroLandQuestion()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> deleteQuestion(HeroLandQuestionDP dp) {
        try {
            heroLandQuestionExtMapper.updateByPrimaryKey(BeanUtil.updateConversion(dp.deleteCheck(), new HeroLandQuestion()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> addTopicQuestions(HeroLandTopicGroupDP dp) {
        dp.addTopicQuestions();
        try {
            HeroLandTopicQuestionExample example = new HeroLandTopicQuestionExample();
            HeroLandTopicQuestionExample.Criteria criteria = example.createCriteria();
            criteria.andTopicIdEqualTo(dp.getTopicId());
            heroLandTopicQuestionExtMapper.deleteByExample(example);
            int count = heroLandTopicQuestionExtMapper.insertBash(BeanUtil.queryListConversion(dp.getQuestions(), HeroLandTopicQuestion.class));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopicQuestions(HeroLandQuestionQO qo) {

        List<HeroLandTopicQuestions> heroLandQuestions = Lists.newArrayList();
        Long count = 0L;
        try {
            heroLandQuestions = heroLandTopicQuestionExtMapper.selectTopicQuestions(qo.topicIdCheck());
            count = heroLandTopicQuestionExtMapper.selectTopicQuestionsCount(qo);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandTopicGroupDP.class);
    }

    @Override
    public ResponseBody<List<HeroLandQuestionDP>> getQuestion(HeroLandQuestionQO qo) {
        List<HeroLandQuestion> heroLandQuestions = Lists.newArrayList();
        long count = 0L;
        try {
            HeroLandQuestionExample example = new HeroLandQuestionExample();
            HeroLandQuestionExample.Criteria criteria = example.createCriteria();
            MybatisCriteriaConditionUtil.createExample(criteria, qo);
            heroLandQuestions = heroLandQuestionExtMapper.selectByExample(example);
            count = heroLandQuestionExtMapper.countByExample(example);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandQuestionDP.class);
    }
}
