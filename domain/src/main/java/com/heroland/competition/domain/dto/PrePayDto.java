package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class PrePayDto implements Serializable {

    /**
     * 支付界面地址
     */
    private String redirectUrl;

    /**
     * 支付单
     */
    private Long payId;
}
