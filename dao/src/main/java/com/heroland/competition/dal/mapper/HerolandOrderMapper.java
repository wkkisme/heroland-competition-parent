package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.order.HerolandOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface HerolandOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandOrder record);

    int insertSelective(HerolandOrder record);

    HerolandOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandOrder record);

    int updateByPrimaryKey(HerolandOrder record);

    List<HerolandOrder> listOrderByBuyer(@Param("userId") String userId,@Param("status") List<String> status);

    List<HerolandOrder> getByBizNos(@Param("bizNos") List<String> bizNos);

    int closeOrders(@Param("closeReason") String closeReason,@Param("closeTime") Date closeTime,@Param("bizNos") List<String> bizNos);
}