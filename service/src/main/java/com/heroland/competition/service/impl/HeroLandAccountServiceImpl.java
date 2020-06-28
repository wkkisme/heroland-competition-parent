package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandAccountExtMapper;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.service.HeroLandAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author mac
 */
@Service
public class HeroLandAccountServiceImpl implements HeroLandAccountService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandAccountServiceImpl.class);
    @Resource
    private RedisTemplate<String, HeroLandAccountDP> redisTemplate;

    @Resource
    private HeroLandAccountExtMapper heroLandAccountExtMapper;

    @Override
    public ResponseBody<Set<HeroLandAccountDP>> getOnLineUserByType(HeroLandAccountDP dp) {
        Set<HeroLandAccountDP> members = redisTemplate.opsForSet().members(dp.getCompetitionType());
        ResponseBody<Set<HeroLandAccountDP>> objectResponseBody = new ResponseBody<>();
        objectResponseBody.setData(members);
        return objectResponseBody;
    }

    @Override
    public ResponseBody<HeroLandAccountDP> getCurrentUserCompetition(HeroLandAccountDP dp) {
        try {
            heroLandAccountExtMapper.getCurrentUserCompetition(BeanUtil.conversion(dp.queryCheck(),new HeroLandAccount() ));
        } catch (Exception e) {
            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }


        return null;
    }
}
