package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class HerolandSkuQO extends BaseQO implements Serializable {

    /**
     * 获取宝石类型
     * 本次 默认 DIAM
     */
    @NotNull
    private String spuId;

    /**
     * 宝石规格id
     */
    private String skuId;

}