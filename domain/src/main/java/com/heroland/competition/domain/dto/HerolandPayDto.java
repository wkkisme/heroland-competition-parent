package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandPayDto extends BaseDO implements Serializable {

    /**
     * 业务订单号
     */
    private String bizNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 用户id
     */
    private String buyId;

    /**
     * 支付工具
     */
    private String payTool;

    /**
     * 支付平台单号
     */
    private String paymentNo;

    /**
     * 支付入口
     * PC APP
     */
    private String payWay;

    /**
     * 支付完成时间
     */
    private Date payFinishTime;

    /**
     * 金额
     */
    private Long settleAmt;

    /**
     * 金额汇率
     */
    private String currencyType;


}