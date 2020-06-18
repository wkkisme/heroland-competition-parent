package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupExtMapper;
import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
            result.setData(heroLandTopicGroupExtMapper.insert(BeanUtil.insertConversion(dp, new HeroLandTopicGroup())) > 0);
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
            result.setData(heroLandTopicGroupExtMapper.deleteByPrimaryKey(dp.getId()) > 0);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> addTopicQuestions(HeroLandTopicGroupDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopicQuestions(HeroLandTopicGroupQO qo) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopic(HeroLandTopicGroupQO qo) {
        return null;
    }
}
