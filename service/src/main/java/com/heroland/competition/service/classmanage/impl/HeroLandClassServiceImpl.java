package com.heroland.competition.service.classmanage.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandClassMapper;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import com.heroland.competition.dal.pojo.HeroLandClass;
import com.heroland.competition.dal.pojo.HeroLandClassExample;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.service.classmanage.HeroLandClassService;
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
}
