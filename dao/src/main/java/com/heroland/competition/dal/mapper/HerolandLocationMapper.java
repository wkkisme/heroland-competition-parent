package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandLocation;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public interface HerolandLocationMapper {

    List<HerolandLocation> getLocationByKey(String key, String code);

    int insert(HerolandLocation location);
}
