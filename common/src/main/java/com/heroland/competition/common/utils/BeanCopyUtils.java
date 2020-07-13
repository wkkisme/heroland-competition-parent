package com.heroland.competition.common.utils;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 */
public class BeanCopyUtils {


    public static <T> T copyByJSON(@NotNull Object source, Class<T> clazz){
        if (source == null) {
            throw new NullPointerException("source can't be null");
        }
        String sourceString  = JSON.toJSONString(source);
        return JSON.parseObject(sourceString,clazz);
    }

    public static <T> List<T> copyArrayByJSON(@NotNull Object source, Class<T> clazz){
        if (source == null) {
            throw new NullPointerException("source can't be null");
        }
        String sourceString  = JSON.toJSONString(source);
        return JSON.parseArray(sourceString,clazz);
    }

    public static <T> T copyByJSONWithNULLSource(@NotNull Object source, Class<T> clazz){
        if (source == null) {
            return null;
        }
        String sourceString  = JSON.toJSONString(source);
        return JSON.parseObject(sourceString,clazz);
    }
}
