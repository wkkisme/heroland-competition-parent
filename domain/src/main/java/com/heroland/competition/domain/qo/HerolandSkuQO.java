package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandSkuQO extends BaseQO implements Serializable {

    private String spuId;

    private String skuId;

}