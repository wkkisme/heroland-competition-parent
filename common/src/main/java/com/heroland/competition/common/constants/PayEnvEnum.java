package com.heroland.competition.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayEnvEnum {

    PC("WEB", "电脑端"),
    H5("H5", "H5"),
    APP("APP", "APP"),


    ;


    String env;

    String name;


    public static PayEnvEnum findByEnv(String env) {
        for (PayEnvEnum channelEnum : values()) {
            if (channelEnum.env.equalsIgnoreCase(env)) {
                return channelEnum;
            }
        }
        return null;
    }
}
