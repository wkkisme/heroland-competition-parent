package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandDiamondStockLog;
import com.heroland.competition.domain.qo.HerolandDiamRecordQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandDiamondStockLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandDiamondStockLog record);

    int insertSelective(HerolandDiamondStockLog record);

    HerolandDiamondStockLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandDiamondStockLog record);

    int updateByPrimaryKey(HerolandDiamondStockLog record);

    List<HerolandDiamondStockLog> getByQuery(@Param("item") HerolandDiamRecordQO request);
}