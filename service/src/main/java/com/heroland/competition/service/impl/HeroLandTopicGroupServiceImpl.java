package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionExtMapper;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupExtMapper;
import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.dal.pojo.HeroLandTopicGroupExample;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.service.HeroLandTopicGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkai
 */
@Service
public class HeroLandTopicGroupServiceImpl implements HeroLandTopicGroupService {

    private final Logger logger = LoggerFactory.getLogger(HeroLandTopicGroupServiceImpl.class);

    @Resource
    private HeroLandTopicGroupExtMapper heroLandTopicGroupExtMapper;



    @Override
    public ResponseBody<Boolean> addTopic(HeroLandTopicGroupDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            result.setData(heroLandTopicGroupExtMapper.insertSelective(BeanUtil.insertConversion(dp.addCheckAndInit(), new HeroLandTopicGroup())) > 0);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> deleteTopic(HeroLandTopicGroupDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            result.setData(heroLandTopicGroupExtMapper.updateByPrimaryKey(BeanUtil.updateConversion(dp.deleteCheck(),new HeroLandTopicGroup())) > 0);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopic(HeroLandTopicGroupQO qo) {
        HeroLandTopicGroupExample heroLandTopicGroupExample = new HeroLandTopicGroupExample();
        MybatisCriteriaConditionUtil.createExample(heroLandTopicGroupExample.createCriteria(),qo);
        List<HeroLandTopicGroup> heroLandTopicGroups = new ArrayList<>();
        long count = 0L;
        try {

            heroLandTopicGroups = heroLandTopicGroupExtMapper.selectByExample(heroLandTopicGroupExample);
            count = heroLandTopicGroupExtMapper.countByExample(heroLandTopicGroupExample);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successListWrapper(heroLandTopicGroups, count, qo, HeroLandTopicGroupDP.class);
    }
}
