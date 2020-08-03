package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private String paymentNo;

    /**
     * 支付渠道
     */
    private String payTool;

    /**
     * 支付完成时间
     */
    private Date payFinishTime;
}
