package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.DiffEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandKnowledgeMapper;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.dto.HerolandKnowledgeDto;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 */
@Component
@Slf4j
public class HerolandKnowledgeServiceImpl implements HerolandKnowledgeService {

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    @Transactional
    public ResponseBody<Boolean> add(HerolandKnowledgeDP dp) {

        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            dp = dp.checkAndBuildBeforeCreate();
            HerolandKnowledge herolandKnowledge = BeanUtil.insertConversion(dp, new HerolandKnowledge());
            herolandKnowledgeMapper.insertSelective(herolandKnowledge);
        } catch (Exception e) {
            log.error("add error, [{}]", JSON.toJSONString(dp));
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    @Transactional
    public ResponseBody<Boolean> edit(HerolandKnowledgeDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            add(dp);
        }else {
            try {
                result.setData(herolandKnowledgeMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp, new HerolandKnowledge())) > 0);
            } catch (Exception e) {
                log.error("edit error", e);
                ResponseBodyWrapper.failSysException();
            }
        }
        return result;
    }

    //看需要再补充
    @Override
    public ResponseBody<Boolean> deleteAllByNode(Long id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseBody<Boolean> deleteOneNode(Long id) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failSysException();
        }
        result.setData(herolandKnowledgeMapper.deleteByPrimaryKey(id) > 0);
        return result;
    }


    @Override
    public HerolandKnowledgeDto getById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            return null;
        }
        HerolandKnowledge herolandKnowledge = herolandKnowledgeMapper.selectByPrimaryKey(id);
        if (Objects.isNull(herolandKnowledge)){
            return null;
        }
        HerolandKnowledgeDto dto = new HerolandKnowledgeDto();
        dto.setCourse(herolandKnowledge.getCourse());
        dto.setGrade(herolandKnowledge.getGrade());
        List<String> keys = Lists.newArrayList();
        keys.add(herolandKnowledge.getCourse());
        keys.add(herolandKnowledge.getGrade());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(herolandKnowledge.getCourse())){
            dto.setCourseName(keyMap.get(herolandKnowledge.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(herolandKnowledge.getGrade())){
            dto.setGradeName(keyMap.get(herolandKnowledge.getGrade()).get(0).getDictValue());
        }
        dto.setDiff(herolandKnowledge.getDiff());
        DiffEnum diffEnum = DiffEnum.valueOfLevel(herolandKnowledge.getDiff());
        dto.setDiffName(Objects.isNull(diffEnum) ? null : diffEnum.getName());
        return dto;
    }

    @Override
    public List<HerolandKnowledgeDto> getByIds(List<Long> ids) {
//        if (CollectionUtils.isEmpty(ids)){
//            return Lists.newArrayList();
//        }
//        List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(ids);
//        if (!CollectionUtils.isEmpty(herolandKnowledges)){
//
//        }
        return null;
    }

    private HerolandKnowledgeDto getAdminData(HerolandKnowledge herolandKnowledge){
        HerolandKnowledgeDto dto = new HerolandKnowledgeDto();
        dto.setCourse(herolandKnowledge.getCourse());
        dto.setGrade(herolandKnowledge.getGrade());
        List<String> keys = Lists.newArrayList();
        keys.add(herolandKnowledge.getCourse());
        keys.add(herolandKnowledge.getGrade());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(herolandKnowledge.getCourse())){
            dto.setCourseName(keyMap.get(herolandKnowledge.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(herolandKnowledge.getGrade())){
            dto.setGradeName(keyMap.get(herolandKnowledge.getGrade()).get(0).getDictValue());
        }
        dto.setKnowledge(herolandKnowledge.getKnowledge());
        dto.setId(herolandKnowledge.getId());
        dto.setDiff(herolandKnowledge.getDiff());
        DiffEnum diffEnum = DiffEnum.valueOfLevel(herolandKnowledge.getDiff());
        dto.setDiffName(Objects.isNull(diffEnum) ? null : diffEnum.getName());
        return dto;
    }

    @Override
    public PageResponse<HerolandKnowledgeDto> pageQuery(HerolandKnowledgeQO qo) {
        List<HerolandKnowledgeDto> result = new ArrayList<>();
        PageResponse<HerolandKnowledgeDto> pageResult = new PageResponse<>();
        Page<HerolandKnowledge> data= PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                () -> herolandKnowledgeMapper.selectByQuery(qo));
        if (!CollectionUtils.isEmpty(data.getResult())){
            data.getResult().stream().forEach(e -> {
                HerolandKnowledgeDto knowledgeDto = getAdminData(e);
                result.add(knowledgeDto);
            });
        }
        pageResult.setItems(result);
        pageResult.setPageSize(data.getPageSize());
        pageResult.setPage(data.getPageNum());
        pageResult.setTotal((int) data.getTotal());
        return pageResult;

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

}
