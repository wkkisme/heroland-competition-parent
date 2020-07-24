package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandAccountMapper {
    long countByExample(HeroLandAccountExample example);

    int deleteByExample(HeroLandAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandAccount record);

    int insertSelective(HeroLandAccount record);

    List<HeroLandAccount> selectByExample(HeroLandAccountExample example);

    HeroLandAccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandAccount record, @Param("example") HeroLandAccountExample example);

    int updateByExample(@Param("record") HeroLandAccount record, @Param("example") HeroLandAccountExample example);

    int updateByPrimaryKeySelective(HeroLandAccount record);

    int updateByPrimaryKey(HeroLandAccount record);
}