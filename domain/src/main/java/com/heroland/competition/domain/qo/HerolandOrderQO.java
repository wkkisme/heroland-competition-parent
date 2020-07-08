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

    @NotNull
    private String buyerId;

    private String buyerName;

    @NotNull
    private String skuId;

    @NotNull
    private Integer skuNum;

}
