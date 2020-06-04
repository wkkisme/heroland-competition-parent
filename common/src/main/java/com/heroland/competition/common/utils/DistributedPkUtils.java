package com.heroland.competition.common.utils;

import com.alijk.bqcommon.base.utils.net.BQIPUtils;

import java.util.UUID;

/**
 * @author Eason@bianque
 * @description 分布式主键构造工具
 * @date 2019-08-18
 **/
public class DistributedPkUtils {

    private static SnowFlake snow;

    static {
        String replace = BQIPUtils.getLocalIpAddr();
        int hashCode = replace.hashCode() < 0 ? ~replace.hashCode() + 1 : replace.hashCode();

        hashCode = hashCode % 31;
        int workId = hashCode;

        int dataCenterId = 0;

        if (hashCode == 31) {
            dataCenterId = workId - 1;
        } else {
            dataCenterId = workId + 1;
        }

        snow = new SnowFlake(workId, dataCenterId);
    }

    /**
     * 雪花算法
     *
     * @return
     */
    public static String snowflake() {

        return snow.nextId();
    }


    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 随机生成 8 位 字符串
     *
     * @return
     */
    public static String getEightBitsUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }

        return shortBuffer.toString();
    }

    public static String getEightBitsUUID(boolean isLowerCase) {
        return getEightBitsUUID().toLowerCase();
    }

    /**
     * 得到32位随机字符串
     *
     * @return
     */
    public static String getUUID() {

        String uuid = UUID.randomUUID().toString().replace("-", "");

        return uuid;
    }

    public static String accessKey() {
        return getUUID().substring(0, 22);
    }

}