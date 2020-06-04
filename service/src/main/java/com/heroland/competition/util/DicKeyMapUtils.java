package com.heroland.competition.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Class Name DicKeyMapConstants ...
 *
 * @author LiJun
 * Created on 2020/3/13 16:36
 */
public class DicKeyMapUtils {

    private static final Logger log = LoggerFactory.getLogger(DicKeyMapUtils.class);


    /**
     * 值域代码
     */
    public static final String DIC_VALUE = "dicValue";

    private static final Set<String> DIC_VALUE_SET = new HashSet<>();

    static {
        DIC_VALUE_SET.add(DIC_VALUE.toLowerCase());
        DIC_VALUE_SET.add("dic_value");
        DIC_VALUE_SET.add("dicCode".toLowerCase());
        DIC_VALUE_SET.add("dic_code");
        DIC_VALUE_SET.add("dicKey".toLowerCase());
        DIC_VALUE_SET.add("dic_key");
        DIC_VALUE_SET.add("key".toLowerCase());
    }

    /**
     * 值域名称
     */
    public static final String DIC_DESC = "dicDesc";
    private static final Set<String> DIC_NAME_SET = new HashSet<>();

    static {
        DIC_NAME_SET.add(DIC_DESC.toLowerCase());
        DIC_NAME_SET.add("dic_desc");
        DIC_NAME_SET.add("value".toLowerCase());
    }

    private static final String SET_DIC_VALUE = "setDicValue";
    private static final String SET_DIC_NAME = "setDicDesc";


    public static <T> List<Map<String, String>> filter(final List<Map<String, Object>> paramsList, List<T> resultList, Class<T> targetClass){

        return filter(paramsList, resultList, targetClass, SET_DIC_VALUE, SET_DIC_NAME);
    }

    /**
     * 过滤调非dic 的列表
     * 可以入参 resultList 来获取对应的类对象
     *
     * @param targetClass 必须包含 setDicValue 和 setDicName 方法
     * @param paramsList
     * @return
     */
    public static <T> List<Map<String, String>> filter(final List<Map<String, Object>> paramsList, List<T> resultList, Class<T> targetClass, String dicValue, String dicDesc) {

        List<Map<String, String>> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(paramsList)) {

            boolean isNeedPutList = null != result && null != targetClass;

            if (isNeedPutList && (StringUtils.isBlank(dicDesc) || StringUtils.isBlank(dicValue))){
                throw new NullPointerException("dicValue, dicDesc");
            }

            paramsList.forEach(params -> {
                if (params.size() > 0) {
                    Map<String, String> map = new HashMap<>();
                    params.forEach((key, value) -> {
                        if (DIC_VALUE_SET.contains(key.toLowerCase())) {
                            map.put(DIC_VALUE, value.toString());
                        } else if (DIC_NAME_SET.contains(key.toLowerCase())) {
                            map.put(DIC_DESC, value.toString());
                        }
                    });

                    if (isNeedPutList) {
                        try {
                            // 通过反射设置属性
                            T t = targetClass.newInstance();
                            Method key = targetClass.getMethod(dicDesc, String.class);
                            key.invoke(t, map.get(DIC_DESC));
                            Method value = targetClass.getMethod(dicValue, String.class);
                            value.invoke(t, map.get(DIC_VALUE));
                            resultList.add(t);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                    }
                    result.add(map);
                }

            });
        }
        return result;

    }

    private DicKeyMapUtils() {

    }
}
