package com.heroland.competition.common.utils;

import com.heroland.competition.common.constant.DataQualityConstants;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Description：
 * 公用字符串工具类
 *
 * @Author 陈波
 * @Create 2018-11-05 14:09
 **/
public class StringUtil {

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

    public static String getLikeValue(String value) {
        if (value != null) {
            value = value.trim();
        }
        return "%" + value + "%";
    }

    public static List<String> split(String value) {
        try {
            if(StringUtils.isBlank(value)){
                return new ArrayList<>();
            }
            return Splitter.on(DataQualityConstants.SIGN_COMMA).trimResults().omitEmptyStrings().splitToList(value);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 过滤不可见字符
     */
    public static String stripNonValidCharacters(String input) {
        if (input == null || ("".equals(input))) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF))) {
                out.append(current);
            }

        }
        return out.toString();
    }



    private static Charset utf8Charset = Charset.forName("UTF-8");

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    public static byte[] getBytesUtf8(final String string) {
        return getBytes(string, utf8Charset);
    }

    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    public static String newStringUtf8(final byte[] bytes) {
        return newString(bytes, utf8Charset);
    }

    private static String newString(final byte[] bytes, final Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }
}
