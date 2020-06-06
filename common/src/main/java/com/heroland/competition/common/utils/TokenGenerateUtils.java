package com.heroland.competition.common.utils;

import java.util.UUID;

/**
 * @author: wk
 * @date: 2018/11/4
 * @instructions: 生成 token
 */
public class TokenGenerateUtils {

    public static String generate(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
