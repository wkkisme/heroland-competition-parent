package com.heroland.competition.qo;

import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.enums.CompetitionEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smjyouzan
 * @date 2020/7/22
 */
@Data
public class HerolandDiamRechargeQO extends BaseQO implements Serializable {

    /**
     * 用户id
     */

    private String userId;

    /**
     * 充值订单
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
