package com.heroland.competition.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: peng.zhup
 * @Project: bq-order-proxy
 * @DateTime: 2019/2/22 14:19
 * @Description: 扁鹊交易流水号生成策略
 */
public class BQTradeNoGenerateUtils {

    private static SnowFlake snowFlake = new SnowFlake(2, 3);


    /** 格式化日期的标准字符串 */
    private final static String DATE_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 使用ThreadLocal定义一个全局的SimpleDateFormat
     * 因为 SimpleDateFormat 不是线程安全
     */
    private static ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_POOL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };


    /**
     * 格式 yyyyMMddHHmmssSSS(17位) + 雪花算法唯一数字生成(18位)
     * exp: 20190222143831378296331963781099528
     * @return
     */
    public static String generate(){
        return SIMPLE_DATE_FORMAT_THREAD_POOL.get().format(new Date()) + snowFlake.nextId();
    }


}
