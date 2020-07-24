package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandDiamondStockLog extends BaseDO {

    private String userId;

    private Integer type;

    private Integer num;

    private String bizGroup;

    private String bizType;

}