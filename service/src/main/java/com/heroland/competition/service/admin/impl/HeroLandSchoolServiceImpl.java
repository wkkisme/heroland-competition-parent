package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HerolandSchoolMapper;
import com.heroland.competition.dal.pojo.HerolandSchool;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.qo.HerolandSchoolQO;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandSchoolService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
@Slf4j
public class HeroLandSchoolServiceImpl implements HeroLandSchoolService {

    @Resource
    private HerolandSchoolMapper herolandSchoolMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    public Boolean addNode(HerolandSchoolDP schoolDP) {
        try {
           return herolandSchoolMapper.insert(BeanUtil.insertConversion(schoolDP.checkAndBuildBeforeCreate(), new HerolandSchool())) > 0;
        } catch (Exception e) {
            log.error("addDict error, [{}]", JSON.toJSONString(schoolDP));
            ResponseBodyWrapper.failException(e.getMessage());
        }
        return true;

    }

    @Override
    public Boolean deleteNode(String key) {
        if (StringUtils.isBlank(key)){
            return true;
        }
        HerolandSchool herolandSchool = herolandSchoolMapper.getByKey(key);
        if (Objects.isNull(herolandSchool)){
            return true;
        }
        //如果是非叶子节点，则下面的所有子节点都需要删除
        List<HerolandSchool> list = herolandSchoolMapper.getByParent(herolandSchool.getKey());
        if (CollectionUtils.isEmpty(list)){
           return herolandSchoolMapper.deleteByPrimaryKey(herolandSchool.getId()) > 0;
        }
        List<HerolandSchool> children = Lists.newArrayList();
        getChildren(herolandSchool.getKey(), children);
        List<Long> ids = children.stream().map(HerolandSchool::getId).distinct().collect(Collectors.toList());
        herolandSchoolMapper.batchDeleteByIds(ids);
        return true;

    }

    @Override
    public List<HerolandSchoolDto> queryChild(HerolandSchoolQO qo) {
        List<HerolandSchoolDto> result = new ArrayList<>();
        if (StringUtils.isBlank(qo.getCode())){
            return null;
        }
        List<HerolandSchool> herolandSchool = herolandSchoolMapper.getByKeyAndCode(qo.getKey(),qo.getCode());
        if (Objects.isNull(herolandSchool)){
            return null;
        }
        result = BeanCopyUtils.copyArrayByJSON(herolandSchool, HerolandSchoolDto.class);
        List<String> keys = herolandSchool.stream().map(HerolandSchool::getKey).distinct().collect(Collectors.toList());
        //如果是非叶子节点，则下面的所有子节点都需要删除
        List<HerolandSchool> list = herolandSchoolMapper.getByParents(keys);
        if (!CollectionUtils.isEmpty(list)){
            Map<String, List<HerolandSchool>> keyMap = list.stream().collect(Collectors.groupingBy(HerolandSchool::getKey));
            for (HerolandSchoolDto dto : result){
                if (keyMap.keySet().contains(dto.getKey())){
                    List<HerolandSchoolDto> child = BeanCopyUtils.copyArrayByJSON(keyMap.get(dto.getKey()), HerolandSchoolDto.class);
                    dto.setChild(child);
                }
            }
        }
        List<String> allKeys = Lists.newArrayList();
        for (HerolandSchoolDto dto : result){
            allKeys.add(dto.getKey());
            dto.getChild().stream().forEach(e -> allKeys.add(e.getKey()));
        }
        ResponseBody<List<HerolandBasicDataDP>> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        if (!dictInfoByKeys.isSuccess() || CollectionUtils.isEmpty(dictInfoByKeys.getData())){
            log.error("query DictInfoByKeys error", JSON.toJSONString(keys));
            return null;
        }
        Map<String, List<HerolandBasicDataDP>> basic = dictInfoByKeys.getData().stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        result.stream().forEach(e -> {
            if (basic.keySet().contains(e.getKey())){
                e.setName(basic.get(e.getKey()).get(0).getDictValue());
            }
        });
        return result;
    }

    private void getChildren(String parent, List<HerolandSchool> children){
        List<HerolandSchool> list = herolandSchoolMapper.getByParent(parent);
        if (!CollectionUtils.isEmpty(list)){
            children.addAll(list);
            for (HerolandSchool sh : list){
                getChildren(sh.getKey(), children);
            }
        }
    }


}
