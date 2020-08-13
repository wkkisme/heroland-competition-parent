package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.order.HerolandPay;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface HerolandPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandPay record);

    int insertSelective(HerolandPay record);

    HerolandPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandPay record);

    int updateByPrimaryKey(HerolandPay record);

    int updateByBizNo(HerolandPay pay);

    List<HerolandPay> selectByExpireTimeAndStates(@Param("time") Date time, @Param("states") List<String> states);

    List<HerolandPay> selectByStates(@Param("time") Date time, @Param("states") List<String> states);

    int updatePayState(@Param("state") String state,@Param("payIds") List<Long> payIds);
}