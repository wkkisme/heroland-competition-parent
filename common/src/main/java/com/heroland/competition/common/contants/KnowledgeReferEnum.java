package com.heroland.competition.common.contants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum KnowledgeReferEnum {
    CHAPTER(1,"章节"),
    QUESTION(2,"题库"),


    ;


    @Getter
    private String name;

    @Getter
    private Integer type;

    KnowledgeReferEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public static KnowledgeReferEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (KnowledgeReferEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static KnowledgeReferEnum valueOfLevel(Integer type) {
        if (NumberUtils.nullOrZero(type)) {
            return null;
        }
        for (KnowledgeReferEnum chapterEnum : values()){
            if (chapterEnum.type.equals(type)){
                return chapterEnum;
            }
        }
        return null;
    }

}
