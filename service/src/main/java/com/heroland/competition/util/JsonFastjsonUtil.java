package com.heroland.competition.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonFastjsonUtil {
    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String json) {
        try {
            JSON.parseObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object json2Bean(String json, Class clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param json
     * @return
     */
    public static Map json2Map(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Iterator iter = jsonObject.keySet().iterator();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (iter.hasNext()) {
            key = (String) iter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }

    /**
     * 从json数组中得到相应java数组
     *
     * @param json
     * @return
     */
    public static Object[] json2BeanArray(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        return jsonArray.toArray();
    }

    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param json
     * @param clazz
     * @return
     */
    public static List json2List(String json, Class clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 从json数组中解析出java字符串数组
     *
     * @param json
     * @return
     */
    public static String[] json2StringArray(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        String[] stringArray = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }

    /**
     * 从json数组中解析出javaLong型对象数组
     *
     * @param json
     * @return
     */
    public static Long[] json2LongArray(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        Long[] longArray = new Long[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            longArray[i] = Long.valueOf(String.valueOf(jsonArray.getLong(i)));
        }
        return longArray;
    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param json
     * @return
     */
    public static Integer[] json2IntegerArray(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        Integer[] integerArray = new Integer[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            integerArray[i] = Integer.valueOf(String.valueOf(jsonArray.getInteger(i)));
        }
        return integerArray;
    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param json
     * @return
     */
    public static Double[] json2DoubleArray(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        Double[] doubleArray = new Double[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            doubleArray[i] = jsonArray.getDouble(i);
        }
        return doubleArray;
    }

    /**
     * json格式字符串转数组
     */
    public static JSONArray json2JsonArray(String json) {
        return JSON.parseArray(json);
    }

    /**
     * 反序列化为json对象
     */
    public static JSONObject json2JsonObj(String json) {
        return JSON.parseObject(json);
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param
     * @return json字串
     */
    public static String bean2Json(Object obj) {

        return JSON.toJSONString(obj);
    }

    /**
     * 将javaBean转化为json对象
     */
    public static JSONObject bean2JsonObj(Object obj) {
        return (JSONObject) JSON.toJSON(obj);
    }

    /**
     * 数组转json格式字符串
     */
    public static String array2Json(String[] array) {
        return JSON.toJSONString(array, true);
    }

    /**
     * list集合转json格式字符串
     */
    public static String list2Json(List list) {
        return JSON.toJSONString(list, true);
    }
}
