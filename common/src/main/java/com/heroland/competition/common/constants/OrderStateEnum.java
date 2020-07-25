package com.heroland.competition.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum {

    // 已创建（对外是初始态）
    CREATED("CREATED", "订单已创建"),

    // 已支付
    PAID("PAID", "已支付"),

    // 已关闭
    CLOSED("CLOSED", "已关闭"),
    ;

    private String code;
    private String desc;

    /**
     * 根据code找枚举
     *
     * @param code
     * @return
     */
    public static OrderStateEnum findByCode(String code) {
        for (OrderStateEnum value : values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }
}
