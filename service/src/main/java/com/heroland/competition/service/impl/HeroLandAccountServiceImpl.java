package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.service.HeroLandAccountService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author mac
 */
@Service
public class HeroLandAccountServiceImpl implements HeroLandAccountService {
    @Resource
    private RedisTemplate<String, HeroLandAccountDP> redisTemplate;

    @Override
    public ResponseBody<Set<HeroLandAccountDP>> getOnLineUserByType(HeroLandAccountDP dp) {
        Set<HeroLandAccountDP> members = redisTemplate.opsForSet().members(dp.getCompetitionType());
        ResponseBody<Set<HeroLandAccountDP>> objectResponseBody = new ResponseBody<>();
        objectResponseBody.setData(members);
        return objectResponseBody;
    }
}
