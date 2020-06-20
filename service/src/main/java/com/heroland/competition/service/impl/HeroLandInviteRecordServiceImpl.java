package com.heroland.competition.service.impl;


import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandInviteRecordMapper;
import com.heroland.competition.dal.pojo.HeroLandInviteRecord;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@Service
public class HeroLandInviteRecordServiceImpl implements HeroLandInviteRecordService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(HeroLandInviteRecordServiceImpl.class);
    @Resource
    private HeroLandInviteRecordMapper heroLandInviteRecordMapper;

    @Override
    public ResponseBody<Boolean> addInvite(HeroLandInviteRecordDP dp) {
        logger.info("邀请记录：{}", JSON.toJSONString(dp));
        try {
            heroLandInviteRecordMapper.insert(BeanUtil.insertConversion(dp.addCheck(), new HeroLandInviteRecord()));
        } catch (Exception e) {
            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> invite(HeroLandInviteRecordDP dp) {
        //todo  定时修改超时或者自己完成比赛的 清楚redis
        return addInvite(dp.inviteCheck(redisTemplate));
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> getInvite(HeroLandInviteRecordDP dp) {
        return null;
    }
}
