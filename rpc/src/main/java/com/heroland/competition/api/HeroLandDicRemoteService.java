package com.heroland.competition.api;

import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;

import java.util.List;

/**
 * 字段查询
 */
public interface HeroLandDicRemoteService {


    /**
     * 根据字典key获取数据值信息
     * @param keys
     * @return
     */
    List<HerolandBasicDataDP> getDictInfoByKeys(List<String> keys);

    /**
     * 根据子节点获取父节点信息
     * 如果keys为空，则是获取那一类，比如获取所有学校的地区信息
     * @param keys
     * @param code
     * @return
     */
    List<HerolandSchoolSimpleDto> getParentBySubNode(List<String> keys, String code);

}
