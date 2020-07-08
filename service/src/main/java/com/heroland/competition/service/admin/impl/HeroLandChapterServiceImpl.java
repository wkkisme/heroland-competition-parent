package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HeroLandChapterMapper;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandChapter;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import com.heroland.competition.service.admin.HeroLandChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 */
@Component
@Slf4j
public class HeroLandChapterServiceImpl implements HeroLandChapterService {


    @Resource
    private HeroLandChapterMapper heroLandChapterMapper;

    @Override
    public ResponseBody<Boolean> add(HerolandChapterDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            result.setData(heroLandChapterMapper.insertSelective(BeanUtil.insertConversion(dp, new HerolandChapter())) > 0);
        } catch (Exception e) {
            log.error("addSubject error, [{}]", JSON.toJSONString(dp));
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> edit(HerolandChapterDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            add(dp);
        }else {
            try {
                result.setData(heroLandChapterMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp, new HerolandChapter())) > 0);
            } catch (Exception e) {
                log.error("editSubject error", e);
                ResponseBodyWrapper.failSysException();
            }
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> delete(HerolandChapterDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            ResponseBodyWrapper.failSysException();
        }
        result.setData(heroLandChapterMapper.deleteByPrimaryKey(dp.getId()) > 0);
        return result;
    }

    @Override
    public ResponseBody<HerolandChapterDP> getById(Long id) {
        HerolandChapter herolandChapter = null;
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failSysException();
        }
        try {
            herolandChapter = heroLandChapterMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(herolandChapter, HerolandChapterDP.class);
    }

    @Override
    public ResponseBody<List<HerolandChapterDP>> pageQuery(HerolandChapterQO qo) {
        Page<HerolandBasicData> dataPage= PageHelper.startPage(qo.getPageNum(), qo.getPageSize(), true).doSelectPage(
                () -> heroLandChapterMapper.selectByQuery(qo));
        return ResponseBodyWrapper.successListWrapper(dataPage.getResult(), dataPage.getTotal(), qo, HerolandChapterDP.class);
    }
}
