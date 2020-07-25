package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum DiffEnum {
    ESAY(1,"容易"),
    HALF_ESAY(2,"较易"),
    MEDIUM(3,"中等"),
    HALF_DIFFICULT(4,"较难"),
    DIFFICULT(5,"难"),

    ;


    @Getter
    private String name;

    @Getter
    private Integer level;

    DiffEnum(Integer level, String name) {
        this.name = name;
        this.level = level;
    }

    public static DiffEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (DiffEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static DiffEnum valueOfLevel(Integer level) {
        if (NumberUtils.nullOrZero(level)) {
            return null;
        }
        for (DiffEnum diffEnum : values()){
            if (diffEnum.level .equals(level)){
                return diffEnum;
            }
        }
        return null;
    }

}
