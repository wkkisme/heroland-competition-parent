package com.heroland.competition.common.contants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum QtYearRangeEnum {
    ONE(1,"近1年"),
    THREE(2,"近3年"),
    FIVE(3,"近5年"),
    MORE(4,"更多"),
    ;


    @Getter
    private String name;

    @Getter
    private Integer type;

    QtYearRangeEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public static QtYearRangeEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (QtYearRangeEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static QtYearRangeEnum valueOfLevel(Integer type) {
        if (NumberUtils.nullOrZero(type)) {
            return null;
        }
        for (QtYearRangeEnum chapterEnum : values()){
            if (chapterEnum.type.equals(type)){
                return chapterEnum;
            }
        }
        return null;
    }

}
