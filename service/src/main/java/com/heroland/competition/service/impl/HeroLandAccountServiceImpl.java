package com.heroland.competition.service.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandAccountExtMapper;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountQO;
import com.heroland.competition.service.HeroLandAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author mac
 */
@Service
public class HeroLandAccountServiceImpl implements HeroLandAccountService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandAccountServiceImpl.class);
    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandAccountExtMapper heroLandAccountExtMapper;

    @Override
    public ResponseBody<Set<Object>> getOnLineUserByType(HeroLandAccountDP dp) {
        Set<Object> members = redisService.sMembers(dp.getCompetitionType());
        ResponseBody<Set<Object>> objectResponseBody = new ResponseBody<>();
        objectResponseBody.setData(members);
        return objectResponseBody;
    }

    @Override
    public ResponseBody<HeroLandAccountDP> getCurrentUserCompetition(HeroLandAccountDP dp) {
        try {
            heroLandAccountExtMapper.getCurrentUserCompetition(BeanUtil.conversion(dp.queryCheck(), new HeroLandAccount()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }


        return null;
    }

    @Override
    public ResponseBody<List<HeroLandAccountDP>> getAccount(HeroLandAccountQO qo) {

        List<HeroLandAccount> heroLandAccounts = null;
        long count = 0;
        try {
            HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
            MybatisCriteriaConditionUtil.createExample(heroLandAccountExample.createCriteria(), qo);
            heroLandAccounts = heroLandAccountExtMapper.selectByExample(heroLandAccountExample);
            count = heroLandAccountExtMapper.countByExample(heroLandAccountExample);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandAccounts, count, qo, HeroLandAccountDP.class);
    }

    @Override
    public ResponseBody<HeroLandAccountDP> getAccountByUserId(String userId) {
        HeroLandAccount account = null;
        try {
            account = heroLandAccountExtMapper.selectByUserId(userId);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successWrapper(account, HeroLandAccountDP.class);
    }
}
