package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandBasicDataMapper {
    int deleteByPrimaryKey(@Param("id")Long id);

    int deleteByDictKey(@Param("dictKeys")List<String> dictKeys);

    int insert(HerolandBasicData record);

    int insertSelective(HerolandBasicData record);

    HerolandBasicData selectByPrimaryKey(@Param("id")Long id);

    List<HerolandBasicData> selectByCode(@Param("code")String code);

    int updateByPrimaryKeySelective(HerolandBasicData record);

    int updateByPrimaryKey(HerolandBasicData record);



    List<HerolandBasicData> selectByDictKeys(@Param("keys") List<String> keys);

    List<HerolandBasicData> selectByCodeAndBizNo(@Param("code")String code, @Param("bizNo")String bizNo);

    List<HerolandBasicData> selectByCodesAndValue(@Param("codes")List<String> codes, @Param("value")String name);
}