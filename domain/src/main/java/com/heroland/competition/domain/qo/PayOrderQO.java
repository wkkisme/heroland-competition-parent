package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class PayOrderQO implements Serializable {

    /**
     * 支付单号
     */
    private Long payId;

    /**
     * 收单号
     */
    private String acquireNo;

    /**
     * 支付渠道
     */
    private String payTool;
}
