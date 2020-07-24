package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author smjyouzan
 * @date 2020/7/22
 */
@Data
public class HerolandDiamondStockDto implements Serializable {

    /**
     * userId
     */
    private String userId;

    /**
     * 库存总量
     */
    private Integer stockNum;

    /**
     * 按月使用明细
     */
    private Map<Integer,List<HerolandDiamMonthRecordDto>> monthRecordMap = Maps.newHashMap();
}
