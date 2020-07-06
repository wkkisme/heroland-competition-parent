package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public interface HerolandLocationMapper {

    List<HerolandLocation> getLocationByKey(@Param("key") String key, @Param("code") String code);

    List<HerolandLocation> getDistinctLocationByCode(@Param("code") String code);

    int insert(HerolandLocation location);
}
