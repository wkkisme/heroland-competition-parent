package com.heroland.competition.common.constants;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public enum DiamBizTypeEnum {
    ANALYSE("ANALYSE","解说分析","GAME"),
    SPY("SPY","侦查敌情","GAME"),
    TIPS("TIPS","解题技巧","GAME"),
    PAY("PAY","解题技巧","BUY"),

    ;


    @Getter
    private String name;

    @Getter
    private String value;

    @Getter
    private String groupName;

    DiamBizTypeEnum(String value, String name, String groupName) {
        this.name = name;
        this.value = value;
        this.groupName = groupName;
    }

    public static DiamBizTypeEnum valueOfV(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (DiamBizTypeEnum diffEnum : values()){
            if (diffEnum.value.equalsIgnoreCase(value)){
                return diffEnum;
            }
        }
        return null;
    }

    public static List<DiamBizTypeEnum> valueOfGroup(String group) {
        List<DiamBizTypeEnum> list = Lists.newArrayList();
        if (StringUtils.isBlank(group)) {
            return Lists.newArrayList();
        }

        for (DiamBizTypeEnum bizTypeEnum : values()){
            if (bizTypeEnum.groupName.equalsIgnoreCase(group)){
               list.add(bizTypeEnum);
            }
        }
        return list;
    }

}
