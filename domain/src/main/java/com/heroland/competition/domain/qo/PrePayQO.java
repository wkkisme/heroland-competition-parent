package com.heroland.competition.domain.qo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class PrePayQO implements Serializable {

    /**
     * 支付单号
     */
    @NotNull
    private Long payId;

    /**
     * 支付渠道
     * 微信|支付宝
     *
     */
    @NotNull
    private String payTool;

    /**
     * 回调url
     */
    @NotNull
    private String returnUrl;

}
