package com.heroland.competition.domain.qo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class HerolandOrderQO implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private String buyerId;

    private String buyerName;

    /**
     * 购买的宝石id
     */
    @NotNull
    private String skuId;

    /**
     * 默认为1
     */
    @NotNull
    private Integer skuNum;

}
