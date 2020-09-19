package com.heroland.competition.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Number 工具类
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 判断数字是否为0或null
     *
     * @param number
     * @return
     */
    public static boolean nullOrZero(Number number) {
        return number == null || number.intValue() == 0;
    }

    public static boolean nullOrZeroLong(Number number) {
        return number == null || number.longValue() == 0;
    }

    public static Integer parseInt(Object data){
        if (data == null) {
            return null;
        } else if (data instanceof Integer) {
            return (Integer) data;
        } else {
            try {
                return Integer.parseInt(StringUtils.trimToNull(data.toString()));
            } catch (Exception e){
                return null;
            }
        }
    }

    public static Long parseLong(Object data){
        if (data == null) {
            return null;
        } else if (data instanceof Long) {
            return (Long) data;
        } else {
            try {
                return Long.parseLong(StringUtils.trimToNull(data.toString()));
            } catch (Exception e){
                return null;
            }
        }
    }


    public static long nextLong(Random rng, long n) {
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }


}
