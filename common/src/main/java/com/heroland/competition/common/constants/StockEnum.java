package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum StockEnum {
    INCREASE(1,"增加"),
    DECREASE(2,"扣减"),

    ;


    @Getter
    private String name;

    @Getter
    private Integer level;

    StockEnum(Integer level, String name) {
        this.name = name;
        this.level = level;
    }

    public static StockEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (StockEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static StockEnum valueOfLevel(Integer level) {
        if (NumberUtils.nullOrZero(level)) {
            return null;
        }
        for (StockEnum diffEnum : values()){
            if (diffEnum.level .equals(level)){
                return diffEnum;
            }
        }
        return null;
    }

}
