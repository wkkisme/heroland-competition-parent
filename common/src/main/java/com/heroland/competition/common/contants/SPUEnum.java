package com.heroland.competition.common.contants;

import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum SPUEnum {
    DIAMOND("DIAM","宝石"),


    ;


    @Getter
    private String name;

    @Getter
    private String desc;

    SPUEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static SPUEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (SPUEnum spuEnum : values()){
            if (spuEnum.name.equals(name)){
                return spuEnum;
            }
        }
        return null;
    }

}
