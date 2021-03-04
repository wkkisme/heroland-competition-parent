package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.HeroLevelEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandAccountExtMapper;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.OnlineDP;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandAccountQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.domain.request.HeroLandTopicPageRequest;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.factory.RobotFactory;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HeroLandTopicGroupService;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.docx4j.wml.P;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Value("${competition.defaultBalance:0}")
    private Long defaultBalance;

    @Override
    public ResponseBody<Set<OnlineDP>> getOnLineUserByType(HeroLandAccountDP dp) {
        Set<Object> members = redisService.sMembers(RedisConstant.ONLINE_KEY + dp.getTopic());
        ResponseBody<Set<OnlineDP>> objectResponseBody = new ResponseBody<>();
        Set<OnlineDP> users = new LinkedHashSet<>();
        List<String> userIds = members.stream().map(String::valueOf).collect(Collectors.toList());
        HeroLandTopicPageRequest heroLandTopicGroupQO = new HeroLandTopicPageRequest();
        heroLandTopicGroupQO.setTopicId(Long.valueOf(dp.getTopicId()));

        Map<String, String> levelMap = getLevel(userIds,heroLandQuestionService.getTopic(heroLandTopicGroupQO).getType(),dp.getUserId());
        members.forEach(userId -> {
            if (userId != null && !userId.equals(dp.getUserId()) && StringUtils.isNotBlank(userId.toString())) {
                Object user = redisService.get("user:" + userId);
                // 如果为空去查下是否有这个人
                if (user != null) {
                    OnlineDP onlineUser = JSON.parseObject(user.toString(), OnlineDP.class);
                    onlineUser.setLevel(levelMap.get(userId.toString()));
                    Set<Object> dps = null;
                    try {
                        dps = redisService.sMembers("recent_user:" + dp.getTopic() + userId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (dps != null && dps.contains(onlineUser.getSenderId())) {

                        PlatformSysUserQO qo = new PlatformSysUserQO();
                        qo.setUserIds(dps.stream().map(String::valueOf).collect(Collectors.toList()));
                        RpcResult<List<PlatformSysUserDP>> userList = platformSsoUserServiceFacade.queryUserList(qo);
                        List<OnlineDP> onlines = new ArrayList<>();
                        if (userList != null && !CollectionUtils.isEmpty(userList.getData())) {
                            userList.getData().forEach(v -> {
                                OnlineDP online = new OnlineDP();
                                online.setSenderId(v.getUserId());
                                online.setSenderName(v.getUserName());
                                if (!CollectionUtils.isEmpty(v.getClasses())) {
                                    online.setClassCode(v.getClasses().get(0).getClassCode());
                                }
                                onlines.add(online);
                            });

                            onlineUser.setRecent(onlines);
                        }
                    } else if (dps != null) {
                        dps.remove(onlineUser.getSenderId());
                        try {
                            redisService.sRemove("recent_user:" + dp.getTopic() + onlineUser.getSenderId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    users.add(onlineUser);
                }

            } else if (userId == null || StringUtils.isBlank(userId.toString())) {
                // 说明不存在 删除
                redisService.sRemove(RedisConstant.ONLINE_KEY + dp.getTopic(), userId);
            }
        });
        Set<OnlineDP> finalUsers = users;
        if (users.size() < 10){
            finalUsers.addAll(RobotFactory.createRobot(10 - users.size(),dp.getTopicId()));
        }
        if (StringUtils.isNotBlank(dp.getLevelCode())){
            finalUsers = users.stream().filter(v -> dp.getLevelCode().equals(v.getLevel())).collect(Collectors.toSet());
        }
        if (dp.getUserStatus() != null){
            finalUsers =  users.stream().filter(v -> dp.getUserStatus().equals(v.getUserStatus())).collect(Collectors.toSet());
        }
        objectResponseBody.setData(finalUsers);
        return objectResponseBody;
    }

    @NotNull
    public Map<String, String> getLevel(List<String> userIds, Integer topicType,String currentUser) {
        Map<String, String> levelMap = new HashMap<>();
        HeroLandStatisticsTotalQO heroLandStatisticsTotalQO = new HeroLandStatisticsTotalQO();
//        heroLandStatisticsTotalQO.setUserIds(userIds);
        heroLandStatisticsTotalQO.setType(topicType);
        heroLandStatisticsTotalQO.setNeedPage(false);
        if (CompetitionEnum.WORLD.getType().equals(topicType)){
            heroLandStatisticsTotalQO.setUserId(currentUser);
        }
        ResponseBody<List<HeroLandStatisticsDetailDP>> competitionsDetail = heroLandCompetitionStatisticsService.getCompetitionsDetail(heroLandStatisticsTotalQO);
        if (competitionsDetail !=null && !CollectionUtils.isEmpty(competitionsDetail.getData())){
            levelMap = new HashMap<>();
            List<HeroLandStatisticsDetailDP> data = competitionsDetail.getData();

            List<HeroLandStatisticsDetailDP> winRank = data.stream().sorted(Comparator.comparing(HeroLandStatisticsDetailDP::getWinRate)).collect(Collectors.toList());
            int size = winRank.size();

            if (size ==1){
                levelMap.put(winRank.get(0).getUserId(),HeroLevelEnum.ADVERSITY_HERO.name());
            }else
            if (size == 2){
                levelMap.put(winRank.get(0).getUserId(),HeroLevelEnum.ADVERSITY_HERO.name());
                levelMap.put(winRank.get(1).getUserId(),HeroLevelEnum.COURAGEOUS_HERO.name());
            }else
            if (size == 3){
                levelMap.put(winRank.get(0).getUserId(),HeroLevelEnum.ADVERSITY_HERO.name());
                levelMap.put(winRank.get(1).getUserId(),HeroLevelEnum.COURAGEOUS_HERO.name());
                levelMap.put(winRank.get(2).getUserId(),HeroLevelEnum.SUPREME_HERO.name());
            }else {
                long ADVERSITY_HERO = Math.round(size * 0.25);
                long SUPREME_HERO = Math.round(size * 0.75);
                for (int i = 0; i < winRank.size(); i++) {
                    if (i <= ADVERSITY_HERO){
                        levelMap.put(winRank.get(i).getUserId(),HeroLevelEnum.ADVERSITY_HERO.name());
                    }else if (i <= SUPREME_HERO){
                        levelMap.put(winRank.get(i).getUserId(),HeroLevelEnum.COURAGEOUS_HERO.name());
                    }else {
                        levelMap.put(winRank.get(i).getUserId(),HeroLevelEnum.SUPREME_HERO.name());
                    }
                }

            }


        }
        return levelMap;
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

            ResponseBody<HeroLandAccountDP> accountByUserId = getAccountByUserId(dp.getUserId(),null);
            logger.info("accountByUserId:{}",JSON.toJSONString(accountByUserId));
            if (accountByUserId == null ||accountByUserId.getData() ==null ) {
                heroLandAccountExtMapper.insertSelective(BeanUtil.insertConversion(dp.addCheck(defaultBalance), new HeroLandAccount()));
            }
        } catch (Exception e) {
            logger.error("", e);
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
            if (qo.getNeedPage()) {
                heroLandAccountExample.setOrderByClause("gmt_create desc limit " + qo.getStartRow() + "," + qo.getPageSize());
            } else {
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
    public ResponseBody<HeroLandAccountDP> getAccountByUserId(String userId,Integer topicType) {
        HeroLandAccount account = null;
        try {
            account = heroLandAccountExtMapper.selectByUserId(userId);

            if(topicType != null) {
                Map<String, String> level = getLevel(Collections.singletonList(userId), topicType,userId);
                String levelCode = level.get(userId);
                if (HeroLevelEnum.COURAGEOUS_HERO.name().equals(levelCode)) {
                    account.setLevelName("奋勇英雄");
                } else if (HeroLevelEnum.SUPREME_HERO.name().equals(levelCode)) {
                    account.setLevelName("至尊英雄");
                } else {
                    account.setLevelName("逆境英雄");
                }
            }
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
        String key = "user_diamond_decr:"+dp.getUserId()+dp.getBizGroup()+dp.getBizName();
        try {
            if (redisService.setNx(key, dp, "PT3S")) {

                HeroLandAccountQO qo = new HeroLandAccountQO();
                qo.setUserId(userId);
                qo.setAccountId(dp.getAccountId());
                ResponseBody<List<HeroLandAccountDP>> account = getAccount(qo);
                List<HeroLandAccountDP> data = account.getData();
                AssertUtils.assertThat(!CollectionUtils.isEmpty(data), "用户账户不存在");
                if (data.get(0).getBalance() == null || data.get(0).getBalance() < dp.getNum()) {
                    throw new AppSystemException(HerolandErrMsgEnum.FUNDS_INSUFFICIENT.getErrorMessage());
                }
                if (data.get(0).getBalance() != null && data.get(0).getBalance() >= dp.getNum()) {
                    HeroLandAccount heroLandAccount = new HeroLandAccount();
                    try {
                        BeanUtil.updateConversion(dp, heroLandAccount);
                    } catch (Exception e) {
                        ResponseBodyWrapper.failSysException();
                    }
                    HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
                    HeroLandAccountExample.Criteria criteria = heroLandAccountExample.createCriteria();
                    criteria.andUserIdEqualTo(userId);
                    heroLandAccount.setBalance(data.get(0).getBalance() - dp.getNum());
                    heroLandAccountExtMapper.updateByExampleSelective(heroLandAccount, heroLandAccountExample);

                    HerolandDiamRequest herolandDiamRequest = new HerolandDiamRequest();
                    herolandDiamRequest.setUserId(userId);
                    herolandDiamRequest.setBizGroup(dp.getBizGroup());
                    herolandDiamRequest.setBizName(dp.getBizName());
                    herolandDiamRequest.setNum(dp.getNum());
                    herolandDiamRequest.setChangeStockType(2);
                    herolandDiamondService.createDiamondRecord(herolandDiamRequest);
                }
            }
        } finally {
            redisService.del(key);
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
            HeroLandAccountDP heroLandAccountDP = null;
            if (CollectionUtils.isEmpty(account.getData())) {
                heroLandAccountDP = account.getData().get(0);
            }
            if (heroLandAccountDP == null) {
                HeroLandAccountDP accountDP = new HeroLandAccountDP();
                accountDP.setUserId(dp.getUserId());
                accountDP.setLevelScore(0);
                accountDP.setBalance(defaultBalance);
                saveAccount(accountDP);
                heroLandAccountDP = accountDP;
            }
            HeroLandAccount heroLandAccount = new HeroLandAccount();
            heroLandAccount.setLevelScore(heroLandAccountDP.getLevelScore() + dp.getScore());
            HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
            HeroLandAccountExample.Criteria criteria = heroLandAccountExample.createCriteria();
            criteria.andUserIdEqualTo(dp.getUserId());
            if (dp.getAccountId() != null) {
                criteria.andAccountIdEqualTo(dp.getAccountId());
            }
            criteria.andIsDeletedEqualTo(false);

            heroLandAccountExtMapper.updateByExampleSelective(BeanUtil.updateConversion(dp, heroLandAccount), heroLandAccountExample);
        } catch (Exception e) {

            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<HeroLandAccountDP> incrUserDiamond(HeroLandAccountManageQO dp) {
        String userId = dp.getUserId();
        dp.queryIncrCheck();
        try {
            HeroLandAccountQO qo = new HeroLandAccountQO();
            qo.setUserId(userId);

//            qo.setAccountId(dp.getAccountId());
            ResponseBody<List<HeroLandAccountDP>> account = getAccount(qo);
            List<HeroLandAccountDP> data = account.getData();
            AssertUtils.assertThat(!CollectionUtils.isEmpty(data), "用户账户不存在");
            HeroLandAccountDP heroLandAccountDp = data.get(0);
            HeroLandAccount heroLandAccount = new HeroLandAccount();
            BeanUtil.updateConversion(dp, heroLandAccount);
            HeroLandAccountExample heroLandAccountExample = new HeroLandAccountExample();
            HeroLandAccountExample.Criteria criteria = heroLandAccountExample.createCriteria();
//            criteria.andAccountIdEqualTo(dp.getAccountId());
            criteria.andUserIdEqualTo(userId);
            if (heroLandAccountDp.getBalance() == null) {
                heroLandAccountDp.setBalance(0L);
            }
            heroLandAccount.setBalance(heroLandAccountDp.getBalance() + dp.getNum());
            heroLandAccountExtMapper.updateByExampleSelective(heroLandAccount, heroLandAccountExample);

        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failException(e.getMessage());

        } finally {
            redisService.del("user_diamond_incr:" + userId);
        }
        return ResponseBodyWrapper.success();
    }
}
