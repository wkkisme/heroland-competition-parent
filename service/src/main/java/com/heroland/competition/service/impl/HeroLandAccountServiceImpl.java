package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandAccountExtMapper;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.OnlineDP;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandAccountQO;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
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

    @Resource
    private HerolandDiamondService herolandDiamondService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @NacosValue("${competition.defaultBalance:0}")
    private Long defaultBalance;

    @Override
    public ResponseBody<Set<OnlineDP>> getOnLineUserByType(HeroLandAccountDP dp) {
        Set<Object> members = redisService.sMembers(RedisConstant.ONLINE_KEY+dp.getTopicId());
        ResponseBody<Set<OnlineDP>> objectResponseBody = new ResponseBody<>();
        Set<OnlineDP> users= new HashSet<>();
        members.forEach(userId ->{
            if (userId != null && !userId .equals(dp.getUserId()) && StringUtils.isNotBlank(userId.toString())) {
                OnlineDP user = (OnlineDP) redisService.get("user:" + userId);
                // 如果为空去查下是否有这个人
                if (user != null) {
                    users.add(user);
                }

            }else if ( userId == null || StringUtils.isBlank(userId.toString())){
                // 说明不存在 删除
                redisService.sRemove(RedisConstant.ONLINE_KEY+dp.getTopicId(),userId);
            }
        });
        objectResponseBody.setData(users);
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
    public ResponseBody<Boolean> saveAccount(HeroLandAccountDP dp) {
        try {

            heroLandAccountExtMapper.insertSelective(BeanUtil.insertConversion(dp.addCheck(defaultBalance),new HeroLandAccount()));
        } catch (Exception e) {
            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandAccountDP>> getAccount(HeroLandAccountQO qo) {

        List<HeroLandAccount> heroLandAccounts = null;
        long count = 0;
        try {
            HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
            MybatisCriteriaConditionUtil.createExample(heroLandAccountExample.createCriteria(), qo);
            if(qo.getNeedPage()) {
                heroLandAccountExample.setOrderByClause("gmt_create desc limit " +qo.getStartRow() +","+qo.getPageSize() );
            }else {
                heroLandAccountExample.setOrderByClause("gmt_create desc");
            }
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

    @Override
    public ResponseBody<HeroLandAccountDP> decrUserDiamond(HeroLandAccountManageQO dp) {
        String userId = dp.getUserId();
        dp.queryDecrCheck();
        try {

            if (!redisService.setNx("user_diamond_decr:" + userId, dp, "PT2h")) {

                HeroLandAccountQO qo = new HeroLandAccountQO();
                qo.setUserId(userId);
                qo.setAccountId(dp.getAccountId());
                ResponseBody<List<HeroLandAccountDP>> account = getAccount(qo);
                List<HeroLandAccountDP> data = account.getData();
                AssertUtils.assertThat(CollectionUtils.isEmpty(data), "用户账户不存在");

                if (data.get(0).getBalance() != null && data.get(0).getBalance() >= dp.getNum()) {
                    HeroLandAccount heroLandAccount = new HeroLandAccount();
                    BeanUtil.updateConversion(dp, heroLandAccount);
                    HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
                    HeroLandAccountExample.Criteria criteria = heroLandAccountExample.createCriteria();
                    criteria.andAccountIdEqualTo(dp.getAccountId());
                    criteria.andUserIdEqualTo(userId);
                    heroLandAccount.setBalance(data.get(0).getBalance() - dp.getNum());
                    heroLandAccountExtMapper.updateByExampleSelective(heroLandAccount, heroLandAccountExample);

                    HerolandDiamRequest herolandDiamRequest = new HerolandDiamRequest();
                    herolandDiamRequest.setUserId(userId);
                    herolandDiamRequest.setBizGroup(dp.getCompetitionType().getType().toString());
                    herolandDiamRequest.setBizName(dp.getCompetitionType().getName());
                    herolandDiamRequest.setNum(dp.getNum());
                    herolandDiamRequest.setChangeStockType(2);
                    herolandDiamondService.createDiamondRecord(herolandDiamRequest);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();

        } finally {
            redisService.del("user_diamond_decr:" + userId);
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<HeroLandAccountDP> incrDecrUserScore(HeroLandAccountManageQO dp) {
        try {
            dp.scoreCheck();
            HeroLandAccountQO heroLandAccountQO = new HeroLandAccountQO();
            heroLandAccountQO.setUserId(dp.getUserId());
            heroLandAccountQO.setPageSize(1);
            ResponseBody<List<HeroLandAccountDP>> account = getAccount(heroLandAccountQO);
            HeroLandAccountDP heroLandAccountDP =null;
            if (!CollectionUtils.isEmpty(account.getData())){
                 heroLandAccountDP = account.getData().get(0);
            }
            if (heroLandAccountDP == null){
                HeroLandAccountDP accountDP = new HeroLandAccountDP();
                accountDP.setUserId(dp.getUserId());
                accountDP.setLevelScore(0);
                accountDP.setBalance(defaultBalance);
                saveAccount(accountDP);
                heroLandAccountDP = accountDP;
            }
            HeroLandAccount heroLandAccount = new HeroLandAccount();
            heroLandAccount.setLevelScore(heroLandAccountDP.getLevelScore()+ dp.getScore());
            heroLandAccountExtMapper.updateByExampleSelective(BeanUtil.updateConversion(dp, heroLandAccount),new HeroLandAccountExample());
        } catch (Exception e) {

            logger.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<HeroLandAccountDP> incrUserDiamond(HeroLandAccountManageQO dp) {
        String userId = dp.getUserId();
        dp.queryIncrCheck();
        try {

            if (!redisService.setNx("user_diamond_incr:" + userId, dp, "PT2h")) {

                HeroLandAccountQO qo = new HeroLandAccountQO();
                qo.setUserId(userId);

                qo.setAccountId(dp.getAccountId());
                ResponseBody<List<HeroLandAccountDP>> account = getAccount(qo);
                List<HeroLandAccountDP> data = account.getData();
                AssertUtils.assertThat(CollectionUtils.isEmpty(data), "用户账户不存在");
                HeroLandAccountDP heroLandAccountDp = data.get(0);
                HeroLandAccount heroLandAccount = new HeroLandAccount();
                BeanUtil.updateConversion(dp, heroLandAccount);
                HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
                HeroLandAccountExample.Criteria criteria = heroLandAccountExample.createCriteria();
                criteria.andAccountIdEqualTo(dp.getAccountId());
                criteria.andUserIdEqualTo(userId);
                if (heroLandAccountDp.getBalance() == null) {
                    heroLandAccountDp.setBalance(0L);
                    heroLandAccount.setBalance(heroLandAccountDp.getBalance() + dp.getNum());
                }
                heroLandAccountExtMapper.updateByExampleSelective(heroLandAccount, heroLandAccountExample);
            }
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();

        } finally {
            redisService.del("user_diamond_incr:" + userId);
        }
        return ResponseBodyWrapper.success();
    }
}
