package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/22
 */
@Data
public class HerolandDiamRequest extends BaseQO implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 扣减库存类型
     * 扣减 | 增加
     * INCREASE(1,"增加"),
     * DECREASE(2,"扣减"),
     *
     */
    @NotNull
    private Integer changeStockType;

    /**
     * 扣减数量
     */
    @NotNull
    private Integer num;

    /**
     * 业务名称
     * ANALYSE("ANALYSE","解说分析"),
     * SPY("SPY","侦查敌情"),
     * TIPS("TIPS","解题技巧"),
     * 传参：ANALYSE ...
     * 目前不完整，新增需要业务方来后端注册
     */
    @NotNull
    private String bizName;

    /**
     * 业务组别
     * 比如有 对赛策略，应试训练等
     *
     *  COMPETITON("GAME","对赛策略"),
     *  TRAIN("TRAIN","应试训练"),
     *  BUY("buy","充值"),
     * 传参：GAME ...
     * 新增组别需要在后端注册
     */
    @NotNull
    private String bizGroup;


}
