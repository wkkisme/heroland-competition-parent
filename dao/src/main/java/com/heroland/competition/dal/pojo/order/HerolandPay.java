package com.heroland.competition.dal.pojo.order;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandPay extends BaseDO implements Serializable {

    private String bizNo;

    private String state;

    private String buyId;

    private String payTool;

    private String paymentNo;

    private Date startTime;

    private Date expireTime;

    private String tradeDesc;

    private String payWay;

    private Date payFinishTime;

    private Long settleAmt;

    private String currencyType;

    private String bizExt;

}