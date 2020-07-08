package com.heroland.competition.common.contants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayCurrencyTypeEnum {
    CNY("CNY",""),

    HKD("HKD",""),


    ;

    String type;

    String name;


    public static PayCurrencyTypeEnum findByType(String type) {
        for (PayCurrencyTypeEnum typeEnum:values()){
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return null;
    }
}
