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
public class HerolandDiamMonthRecordRequest extends BaseQO implements Serializable {

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
     * 业务名称
     * ANALYSE("ANALYSE","解说分析"),
     * SPY("SPY","侦查敌情"),
     * TIPS("TIPS","解题技巧"),
     * 传参：ANALYSE ...
     * 目前不完整，新增需要业务方来后端注册
     */

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
     *
     * 如果只传入bizGroup，会获取该bizGroup下所有biz_type事件的统计信息
     */
    @NotNull
    private String bizGroup;

    /**
     * 该接口是为按月统计
     * 传入某一年就会返回当年每个月的总和
     * 如果不传，默认当前年份当前月至1月的数据，最大一次返回12个月份
     */
    private String year;

}
