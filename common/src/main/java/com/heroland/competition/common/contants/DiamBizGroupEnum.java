package com.heroland.competition.common.contants;

import com.heroland.competition.common.utils.NumberUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum DiamBizGroupEnum {
    COMPETITON("GAME","对赛策略"),
    TRAIN("TRAIN","应试训练"),
    BUY("BUY","充值"),

    ;


    @Getter
    private String name;

    @Getter
    private String group;

    DiamBizGroupEnum(String group, String name) {
        this.name = name;
        this.group = group;
    }

    public static DiamBizGroupEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (DiamBizGroupEnum diffEnum : values()){
            if (diffEnum.name.equals(name)){
                return diffEnum;
            }
        }
        return null;
    }

    public static DiamBizGroupEnum valueOfLevel(String group) {
        if (StringUtils.isBlank(group)) {
            return null;
        }
        for (DiamBizGroupEnum chapterEnum : values()){
            if (chapterEnum.group.equalsIgnoreCase(group)){
                return chapterEnum;
            }
        }
        return null;
    }

}
