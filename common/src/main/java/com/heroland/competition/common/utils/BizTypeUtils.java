package com.heroland.competition.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author: peng.zhup
 * @date: 2018/11/3
 * @instructions: 用于判断bizType传参是否满足要求
 */
public class BizTypeUtils {

    private static String[] strings = {"1", "2", "3", "4", "7", "8"};

    private static List<String> list = Arrays.asList(strings);

    public static boolean isContains(String str) {
        return list.contains(str);
    }

}
