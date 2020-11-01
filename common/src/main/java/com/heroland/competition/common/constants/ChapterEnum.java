package com.heroland.competition.common.constants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum ChapterEnum {
    ZHANG(1,"chapter"),
    KEJIE(2,"unit"),
    JIE(3,"section"),

    ;


    @Getter
    private String name;

    @Getter
    private Integer type;

    ChapterEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public static ChapterEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (ChapterEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static ChapterEnum valueOfLevel(Integer type) {
        if (NumberUtils.nullOrZero(type)) {
            return null;
        }
        for (ChapterEnum chapterEnum : values()){
            if (chapterEnum.type.equals(type)){
                return chapterEnum;
            }
        }
        return null;
    }

}
