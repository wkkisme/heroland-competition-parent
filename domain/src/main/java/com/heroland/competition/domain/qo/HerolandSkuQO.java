package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandSkuQO extends BaseQO implements Serializable {

    /**
     * 获取宝石类型
     * 本次 默认 DIAM
     */
    private String spuId;

    /**
     * 宝石规格id
     */
    private String skuId;

}