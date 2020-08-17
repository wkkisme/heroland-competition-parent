package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @date 2020/7/4
 * 1是分析、2是推理、3是歸納
 */
public enum BankTypeEnum {
    HOMEWORK(1,"作業賽"),
    EXAM(2,"應試賽"),
    INTERSCHOOL(3,"校際賽"),
    WORD(4,"世界賽"),


    ;


    @Getter
    private String name;

    @Getter
    private Integer level;

    BankTypeEnum(Integer level, String name) {
        this.name = name;
        this.level = level;
    }

    public static BankTypeEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (BankTypeEnum bankTypeEnum : values()){
            if (bankTypeEnum.name.equals(name)){
                return bankTypeEnum;
            }
        }
        return null;
    }

    public static BankTypeEnum valueOfLevel(Integer level) {
        if (NumberUtils.nullOrZero(level)) {
            return null;
        }
        for (BankTypeEnum bankTypeEnum : values()){
            if (bankTypeEnum.level .equals(level)){
                return bankTypeEnum;
            }
        }
        return null;
    }

}
