package com.heroland.competition.service.impl;


import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.dal.mapper.HeroLandInviteRecordExtMapper;
import com.heroland.competition.dal.mapper.HeroLandInviteRecordMapper;
import com.heroland.competition.dal.pojo.HeroLandInviteRecord;
import com.heroland.competition.dal.pojo.HeroLandInviteRecordExample;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@Service
public class HeroLandInviteRecordServiceImpl implements HeroLandInviteRecordService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(HeroLandInviteRecordServiceImpl.class);
    @Resource
    private HeroLandInviteRecordExtMapper heroLandInviteRecordMapper;

    @Override
    public ResponseBody<String> addInvite(HeroLandInviteRecordDP dp) {
        logger.info("邀请记录：{}", JSON.toJSONString(dp));
        HeroLandInviteRecord heroLandInviteRecord = null;
        try {
            heroLandInviteRecord = BeanUtil.insertConversion(dp.addCheck(), new HeroLandInviteRecord());
            heroLandInviteRecordMapper.insert(heroLandInviteRecord);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandInviteRecord.getRecordId());
    }

    @Override
    public ResponseBody<String> invite(HeroLandInviteRecordDP dp) {
        return addInvite(dp.inviteCheck(redisTemplate));
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.DO_NOT_AGREE.getStatus());
        if (updateInvite(dp).isSuccess()) {
            dp.finishBeInviteUserCompetition(redisTemplate);
            dp.finishInviteUserCompetition(redisTemplate);
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo) {
        List<HeroLandInviteRecord> heroLandQuestions = Lists.newArrayList();
        long count = 0L;
        try {
            HeroLandInviteRecordExample example = new HeroLandInviteRecordExample();
            HeroLandInviteRecordExample.Criteria criteria = example.createCriteria();
            MybatisCriteriaConditionUtil.createExample(criteria, qo);
            heroLandQuestions = heroLandInviteRecordMapper.selectByExample(example);
            count = heroLandInviteRecordMapper.countByExample(example);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandInviteRecordDP.class);
    }

    @Override
    public ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.AGREE.getStatus());
        return updateInvite(dp);
    }

    @Override
    public ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp) {

        try {

            heroLandInviteRecordMapper.updateByRecordIdSelective(BeanUtil.updateConversion(dp.addCheck(), new HeroLandInviteRecord()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }
}
