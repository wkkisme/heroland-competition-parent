package com.heroland.competition.common.utils;


import com.heroland.competition.common.sm4.SM4Utils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 主索引生成
 *
 * @author chenli
 */
public class MainIndexUtil {

    /**
     * 主索引生成规则（按优先级传入）：
     * * 1、特殊信息：证件号、卡号
     * * 2、固定信息：姓名、性别、出生日期等
     * * 3、易变信息：地址、联系人、婚姻状况、职业、文化程度等
     * * 以上信息请按照优先级顺序传入，截取传入信息12位到27位，用以生成患者主索引信息
     * *   1) 若只有一个参数切长于27直接舍弃补0.
     * *   2) 若多个参数,其中某个参数长于27将舍弃这个参数.
     * *   3) 若所有参数加起来不到12位则补0补齐
     *
     * @param idNum
     * @param idName
     * @param idType
     * @return
     */
    public static String createMainIndex(String idNum, String idName, String idType) {
        String indexStr = indexStr(idNum, idName, idType);
        System.out.println(indexStr.length());
        return Objects.requireNonNull(SM4Utils.encryptData_ECB(indexStr)).toUpperCase();
    }

    public static String createMainIndex(String name, String sex, String phoneNum, String birth) {
        String indexStr = indexStr(name, sex, phoneNum, birth);
        System.out.println(indexStr.length());
        return Objects.requireNonNull(SM4Utils.encryptData_ECB(indexStr)).toUpperCase();
    }

    public static String findMainIndex(String indexData) {
        return SM4Utils.decryptData_ECB(indexData);
    }

    public static String[] findMainIndexArr(String indexData) {
        return Objects.requireNonNull(SM4Utils.decryptData_ECB(indexData)).split(SPLIT);
    }

    private static final String SPLIT = "^";


    private static final String NULL = "NULL";


    private static final String FILL_IN = "0";



    private static String indexStr(String idNum, String idType, String name) {
        return indexStrs(idNum,idType,name);
    }

    private static String indexStr(String name, String sex, String phoneNum, String birth) {

        return indexStrs(name, sex, phoneNum, birth);
    }

    private static String indexStrs(String... params) {
        long l = System.currentTimeMillis();
        int mixLength = 18;
        int maxLength = 31;
        StringBuilder pa = new StringBuilder();
        StringBuilder result = new StringBuilder();
        int length = params.length;
        for (int i = 0; i < length; i++) {

            String param = params[i];
            if (param.getBytes(StandardCharsets.UTF_8).length > maxLength) {
                continue;
            }
            if (StringUtils.isBlank(param)) {
                param = NULL;
            }
            if (pa.toString().getBytes(StandardCharsets.UTF_8).length > maxLength) {
                continue;
            }

            if (pa.toString().getBytes(StandardCharsets.UTF_8).length + param.getBytes(StandardCharsets.UTF_8).length > maxLength) {
                continue;
            }
            pa.append(param);

            if (i != length - 1 && pa.toString().getBytes(StandardCharsets.UTF_8).length != maxLength) {
                pa.append(SPLIT);
            }
            if (i == length - 1 && pa.toString().getBytes(StandardCharsets.UTF_8).length < mixLength) {
                pa.append(SPLIT);
                for (int j = 0; j < mixLength - pa.toString().getBytes(StandardCharsets.UTF_8).length; j++) {
                    pa.append(FILL_IN);
                }
            }

        }
        result.append(pa);

        if (result.toString().getBytes(StandardCharsets.UTF_8).length < mixLength) {
            int lastLength = result.toString().getBytes(StandardCharsets.UTF_8).length;
            for (int j = 0; j < mixLength - lastLength; j++) {
                result.append(FILL_IN);
            }
        }
        String s = result.toString();

        long l1 = System.currentTimeMillis();
        System.out.println("耗时" + (l1 - l));
        return s;
    }


    private static byte[] getBytes(String p) {

        return p.getBytes(StandardCharsets.UTF_8);
    }

    private static String[] indexArr(String indexStr) {
        return indexStr.split(SPLIT);
    }


    public static void main(String[] args) {
        String index = createMainIndex("111111111111111111111111", "aaaa", "aa","");
        System.out.println("index=" + index + ", " + index.length());
        System.out.println("6E077272CF1249706A0EBFF2531BF9FC6E077272CF1249706A0EBFF2531BF9FC13BC1DADFD1A02B4712E7E3426C051BB".equals(index));
        index = findMainIndex(index);
        System.out.println("index=" + index + ", " + index.length());



    }
}
