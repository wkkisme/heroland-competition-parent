package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandBasicData;

import java.util.List;

public interface HerolandBasicDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandBasicData record);

    int insertSelective(HerolandBasicData record);

    HerolandBasicData selectByPrimaryKey(Long id);

    List<HerolandBasicData> selectByCode(String code);

    int updateByPrimaryKeySelective(HerolandBasicData record);

    int updateByPrimaryKey(HerolandBasicData record);

    int updateBySubject(Long subject);


    List<HerolandBasicData> selectByDictKeys(List<String> keys);
}