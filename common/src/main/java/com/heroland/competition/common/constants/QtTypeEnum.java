package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum QtTypeEnum {
    OPTION(1,"选择题"),
    OPTION_ONE(11,"单选题"),
    OPTION_MULTI(12,"多选题"),
    FILL(2,"填空题"),
    ANSWER(3,"解答题"),
    COMPO(4,"综合题"),

    ;


    @Getter
    private String name;

    @Getter
    private Integer type;

    QtTypeEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public static QtTypeEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (QtTypeEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static QtTypeEnum valueOfLevel(Integer type) {
        if (NumberUtils.nullOrZero(type)) {
            return null;
        }
        for (QtTypeEnum chapterEnum : values()){
            if (chapterEnum.type.equals(type)){
                return chapterEnum;
            }
        }
        return null;
    }

}
