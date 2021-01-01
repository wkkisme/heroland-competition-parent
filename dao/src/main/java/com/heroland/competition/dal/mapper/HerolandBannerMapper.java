package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandBanner;
import com.heroland.competition.dal.pojo.HerolandBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HerolandBannerMapper {
    long countByExample(HerolandBannerExample example);

    int deleteByExample(HerolandBannerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HerolandBanner record);

    int insertSelective(HerolandBanner record);

    List<HerolandBanner> selectByExample(HerolandBannerExample example);

    HerolandBanner selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HerolandBanner record, @Param("example") HerolandBannerExample example);

    int updateByExample(@Param("record") HerolandBanner record, @Param("example") HerolandBannerExample example);

    int updateByPrimaryKeySelective(HerolandBanner record);

    int updateByPrimaryKey(HerolandBanner record);
}