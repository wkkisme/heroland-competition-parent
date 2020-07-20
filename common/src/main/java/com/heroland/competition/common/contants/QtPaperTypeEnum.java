package com.heroland.competition.common.contants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;

/**
 * @author smjyouzan
 * @date 2020/7/4
 *
 * 32→模拟考 
 * 34→期末考
 *  35→其中考 
 * 36→月考 
 * 31→中考
 *  38→单元测试
 *  37→其他类型 
 * 33→高考 
 *
 */
public enum QtPaperTypeEnum {
    MONI(32,"模拟考"),
    QIMO(34,"期末考"),
    QIZHONG(35,"期中考"),
    YUE(36,"月考"),
    ZHONG(31,"中考"),
    DANYUAN(38,"单元测试"),
    GAO(37,"高考"),
    OTHER(33,"其他"),

    ;


    @Getter
    private String name;

    @Getter
    private Integer type;

    QtPaperTypeEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public static QtPaperTypeEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (QtPaperTypeEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static QtPaperTypeEnum valueOfLevel(Integer type) {
        if (NumberUtils.nullOrZero(type)) {
            return null;
        }
        for (QtPaperTypeEnum chapterEnum : values()){
            if (chapterEnum.type.equals(type)){
                return chapterEnum;
            }
        }
        return null;
    }

}
