package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandSku;
import com.heroland.competition.domain.qo.HerolandSkuQO;

import java.util.List;

public interface HerolandSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandSku record);

    int insertSelective(HerolandSku record);

    HerolandSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandSku record);

    int updateByPrimaryKey(HerolandSku record);

    List<HerolandSku> list(HerolandSkuQO skuQO);
}