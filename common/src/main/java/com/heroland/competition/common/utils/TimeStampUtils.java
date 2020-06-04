package com.heroland.competition.common.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * @author: peng.zhup
 * @date: 2018/11/3
 * @instructions:  时间戳工具类
 */
public class TimeStampUtils {

    /**
     * 返回是否非法
     *
     * @param timeStamp
     * @return
     */
    public static boolean isLegal(String timeStamp){
        if(StringUtils.isEmpty(timeStamp)){
            return true;
        }
        long past = Long.valueOf(timeStamp);
        long now = System.currentTimeMillis();

        long diffPN = (past - now) / 1000;
        long diffNP = (now - past) / 1000 / 60;
        /* 如果请求时间大于一分钟，或者超过当前时间20秒都算非法 */
        if( diffNP >= 1 || diffPN  > 20 ){
            return true;
        }
        return false;
    }

}
