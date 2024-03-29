package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandPayDP extends BaseDO implements Serializable {

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


    public HerolandPayDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(buyId);
        AssertUtils.notBlank(currencyType);
        AssertUtils.notBlank(bizNo);
        this.state = OrderStateEnum.CREATED.getCode();
        this.startTime = new Date();
        //约定超过25min钟后自动关单
        this.expireTime = DateUtils.plusMill(this.startTime,25*60*1000);
        this.setId(IDGenerateUtils.getIdByRandom(IDGenerateUtils.ModelEnum.PAY));
        return this;
    }

}