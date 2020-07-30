package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @date 2020/7/4
 * 1是分析、2是推理、3是歸納
 */
public enum ThinkEnum {
    ANALYSE(1,"分析"),
    INFER(2,"推理"),
    SUM(3,"歸納"),


    ;


    @Getter
    private String name;

    @Getter
    private Integer level;

    ThinkEnum(Integer level, String name) {
        this.name = name;
        this.level = level;
    }

    public static ThinkEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (ThinkEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static ThinkEnum valueOfLevel(Integer level) {
        if (NumberUtils.nullOrZero(level)) {
            return null;
        }
        for (ThinkEnum diffEnum : values()){
            if (diffEnum.level .equals(level)){
                return diffEnum;
            }
        }
        return null;
    }

}
