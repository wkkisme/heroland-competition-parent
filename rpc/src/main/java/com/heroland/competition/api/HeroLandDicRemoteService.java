package com.heroland.competition.api;

import com.heroland.competition.domain.dp.HerolandBasicDataDP;

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
}
