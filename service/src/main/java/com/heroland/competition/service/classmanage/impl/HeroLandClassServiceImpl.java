package com.heroland.competition.service.classmanage.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandClassMapper;
import com.heroland.competition.dal.mapper.HeroLandUserClassMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandUserClassDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.domain.qo.HeroLandUserClassQO;
import com.heroland.competition.service.classmanage.HeroLandClassService;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 班级管理
 *
 * @author wangkai
 */
@Service
@Slf4j
public class HeroLandClassServiceImpl implements HeroLandClassService {

    @Resource
    private HeroLandClassMapper heroLandClassMapper;

    @Resource
    private HeroLandUserClassMapper heroLandUserClassMapper;

    @Resource
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @Override
    public ResponseBody<Boolean> addClass(HeroLandClassDP dp) {

        try {
            heroLandClassMapper.insert(BeanUtil.insertConversion(dp.addCheck(), new HeroLandClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateClass(HeroLandClassDP dp) {

        try {
            heroLandClassMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.addCheck(), new HeroLandClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<List<HeroLandClassDP>> getClassList(HeroLandClassManageQO qo) {


        List<HeroLandClass> heroLandAccounts = null;
        long count = 0;
        try {
            HeroLandClassExample heroLandAccountExample = new HeroLandClassExample();
            MybatisCriteriaConditionUtil.createExample(heroLandAccountExample.createCriteria(), qo);
            heroLandAccounts = heroLandClassMapper.selectByExample(heroLandAccountExample);
            count = heroLandClassMapper.countByExample(heroLandAccountExample);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandAccounts, count, qo, HeroLandClassDP.class);
    }

    @Override
    public ResponseBody<Boolean> deleteClassList(HeroLandClassManageQO qo) {

        try {
            AssertUtils.assertThat(qo.getId() != null);
            HeroLandClass heroLandClass = new HeroLandClass();
            heroLandClass.setIsDeleted(true);
            heroLandClass.setId(qo.getId());
            heroLandClass.beforeDelete();
            heroLandClassMapper.updateByPrimaryKeySelective(heroLandClass);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> addClassUser(HeroLandUserClassDP dp) {
        try {
            heroLandUserClassMapper.insertSelective(BeanUtil.insertConversion(dp.addCheck(),new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateClassUser(HeroLandUserClassDP dp) {
        try {
            heroLandUserClassMapper.insertSelective(BeanUtil.insertConversion(dp.updateCheck(),new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> deleteClassUser(HeroLandUserClassDP dp) {
        try {
            dp.setIsDeleted(true);
            heroLandUserClassMapper.updateByPrimaryKeySelective(BeanUtil.insertConversion(dp.updateCheck(),new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("",e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<List<HeroLandUserClassDP>> getClassUser(HeroLandUserClassQO qo) {


        List<HeroLandUserClass> heroLandAccounts = null;
        long count = 0;
        try {
            HeroLandUserClassExample heroLandAccountExample = new HeroLandUserClassExample();
            MybatisCriteriaConditionUtil.createExample(heroLandAccountExample.createCriteria(), qo);
            heroLandAccounts = heroLandUserClassMapper.selectByExample(heroLandAccountExample);
            count = heroLandUserClassMapper.countByExample(heroLandAccountExample);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandAccounts, count, qo, HeroLandUserClassDP.class);
    }
}
