package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandKnowledgeMapper;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/7/5
 */
@Component
@Slf4j
public class HerolandKnowledgeServiceImpl implements HerolandKnowledgeService {

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Override
    @Transactional
    public ResponseBody<Boolean> add(HerolandKnowledgeDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            HerolandKnowledge herolandKnowledge = BeanUtil.insertConversion(dp, new HerolandKnowledge());
            herolandKnowledgeMapper.insert(herolandKnowledge);
            if (Objects.equals(Boolean.TRUE,dp.getIsRoot()) && !NumberUtils.nullOrZeroLong(herolandKnowledge.getId())){
                herolandKnowledge.setRootKnowledgeId(herolandKnowledge.getId());
                herolandKnowledge.setParentKnowledgeId(0L);
                herolandKnowledgeMapper.updateByPrimaryKey(herolandKnowledge);
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("add error, [{}]", JSON.toJSONString(dp));
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> edit(HerolandKnowledgeDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            add(dp);
        }else {
            try {
                result.setData(herolandKnowledgeMapper.updateByPrimaryKey(BeanUtil.updateConversion(dp, new HerolandKnowledge())) > 0);
            } catch (Exception e) {
                log.error("edit error", e);
                ResponseBodyWrapper.failSysException();
            }
        }
        return result;
    }

    //看需要再补充
    @Override
    public ResponseBody<Boolean> deleteAllByNode(HerolandKnowledgeQO qo) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> deleteOneNode(HerolandKnowledgeQO qo) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(qo.getId())){
            ResponseBodyWrapper.failSysException();
        }
        result.setData(herolandKnowledgeMapper.deleteByPrimaryKey(qo.getId()) > 0);
        HerolandKnowledge herolandKnowledge = herolandKnowledgeMapper.selectByPrimaryKey(qo.getId());
        if (Objects.equals(Boolean.TRUE,herolandKnowledge.getIsRoot())){
            return result;
        }
        List<HerolandKnowledge> list = herolandKnowledgeMapper.getByParentId(qo.getId());
        list.stream().forEach(e -> e.setParentKnowledgeId(herolandKnowledge.getParentKnowledgeId()));
        list.stream().forEach(e -> herolandKnowledgeMapper.updateByPrimaryKey(e));
        result.setData(true);
        return result;
    }


    @Override
    public ResponseBody<HerolandKnowledgeDP> getById(HerolandKnowledgeQO qo) {
        ResponseBody<HerolandKnowledgeDP> result = new ResponseBody<>();
        HerolandKnowledge herolandKnowledge = null;
        if (NumberUtils.nullOrZeroLong(qo.getId())){
            ResponseBodyWrapper.failSysException();
        }
        try {
            herolandKnowledge = herolandKnowledgeMapper.selectByPrimaryKey(qo.getId());
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        List<HerolandKnowledge> children = Lists.newArrayList();
        if (!Objects.equals(Boolean.TRUE, qo.getIncludeChildren())){
            return ResponseBodyWrapper.successWrapper(herolandKnowledge, HerolandKnowledgeDP.class);
        }
        HerolandKnowledgeDP herolandKnowledgeDP = null;
        try {
            herolandKnowledgeDP = BeanUtil.conversion(herolandKnowledge, new HerolandKnowledgeDP());
            getChildren(qo.getId(),children);
            List<HerolandKnowledgeDP> dpList = children.stream().map(e -> convert(e)).collect(Collectors.toList());
            herolandKnowledgeDP.setChildren(dpList);
        } catch (Exception e) {
            log.error("convert error", e);
            ResponseBodyWrapper.failSysException();
        }
        result.setData(herolandKnowledgeDP);
        return result;
    }

    @Override
    public ResponseBody<List<HerolandKnowledgeDP>> pageQuery(HerolandKnowledgeQO qo) {
        Page<HerolandBasicData> dataPage= PageHelper.startPage(qo.getPageNum(), qo.getPageSize(), true).doSelectPage(
                () -> herolandKnowledgeMapper.selectByQuery(qo));
        return ResponseBodyWrapper.successListWrapper(dataPage.getResult(), dataPage.getTotal(), qo,  HerolandKnowledgeDP.class);
    }

    private void getChildren(Long parentId, List<HerolandKnowledge> children){
        List<HerolandKnowledge> list = herolandKnowledgeMapper.getByParentId(parentId);
        if (!CollectionUtils.isEmpty(list)){
            children.addAll(list);
            for (HerolandKnowledge knowledge : list){
                getChildren(knowledge.getId(), children);
            }
        }
    }

    private HerolandKnowledgeDP convert(HerolandKnowledge herolandKnowledge){
        HerolandKnowledgeDP herolandKnowledgeDP = new HerolandKnowledgeDP();
        herolandKnowledgeDP.setId(herolandKnowledge.getId());
        herolandKnowledgeDP.setChapterId(herolandKnowledge.getChapterId());
        herolandKnowledgeDP.setDiff(herolandKnowledge.getDiff());
        herolandKnowledgeDP.setIsRoot(herolandKnowledge.getIsRoot());
        herolandKnowledgeDP.setKnowledge(herolandKnowledge.getKnowledge());
        herolandKnowledgeDP.setParentKnowledgeId(herolandKnowledge.getParentKnowledgeId());
        herolandKnowledgeDP.setRootKnowledgeId(herolandKnowledge.getRootKnowledgeId());
        return herolandKnowledgeDP;
    }


}
