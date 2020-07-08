package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.order.HerolandPay;

public interface HerolandPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandPay record);

    int insertSelective(HerolandPay record);

    HerolandPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandPay record);

    int updateByPrimaryKey(HerolandPay record);

    int updateByBizNo(HerolandPay pay);
}