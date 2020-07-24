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
     * 是否需要调用支付界面，当为fasle时表示实付金额为0的时候是不用走支付界面的
     * 可以直接跳转到支付成功的页面
     */
    private Boolean needRedirect = true;

    /**
     * 支付单
     */
    private Long payId;
}
