package com.heroland.competition.common.utils;


import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.ResponseBodyWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;


public abstract class AssertUtils {
    /**
     * 断言，必须满足条件
     *
     * @param condition
     */
    public static void assertThat(boolean condition) {
        if (!condition) {
            assertFail();
        }
    }

    /**
     * 断言，必须满足条件，带错误信息
     *
     * @param condition
     * @param msg
     */
    public static void assertThat(boolean condition, String msg) {
        if (!condition) {
            assertFail(msg);
        }
    }

    /**
     * 断言，必须满足条件，带错误信息
     *
     * @param condition
     * @param e
     */
    public static void assertThat(boolean condition, AppSystemException e) {
        if (!condition) {
            throw e;
        }
    }

    /**
     * 断言，Objects.equals(a,b) 为真
     *
     * @param a
     * @param b
     */
    public static void equals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            assertFail();
        }
    }

    /**
     * 断言，Objects.equals(a,b) 为真，带错误信息
     *
     * @param a
     * @param b
     * @param msg
     */
    public static void equals(Object a, Object b, String msg) {
        if (!Objects.equals(a, b)) {
            assertFail(msg);
        }
    }

    /**
     * 断言，obj非空
     *
     * @param obj
     */
    public static void notNull(Object obj) {
        if (obj == null) {
            assertFail();
        }
    }

    /**
     * 断言，obj非空，带错误信息
     *
     * @param obj
     */
    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            assertFail(msg);
        }
    }

    /**
     * 断言，string非空，带错误信息
     *
     * @param str
     */
    public static void notBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            assertFail(msg);
        }
    }

    /**
     * 断言，string非空
     *
     * @param str
     */
    public static void notBlank(String str) {
        if (StringUtils.isBlank(str)) {
            assertFail();
        }
    }

    /**
     * 对集合，map和collection的断言
     * @param obj
     */
    public static void notEmpty(Object obj) {
        if (obj instanceof Collection){
            Collection collection = (Collection)obj;
            if (collection == null || CollectionUtils.isEmpty((Collection)obj)) {
                assertFail();
            }
        }else if(obj instanceof Map){
            Map map = (Map)obj;
            if (map == null || map.size() ==  0){
                assertFail();
            }
        }
    }

    /**
     * 对集合，map和collection的断言
     * @param obj
     */
    public static void notEmpty(Object obj, String msg) {
        if (obj instanceof Collection){
            Collection collection = (Collection)obj;
            if (collection == null || CollectionUtils.isEmpty((Collection)obj)) {
                assertFail(msg);
            }
        }else if(obj instanceof Map){
            Map map = (Map)obj;
            if (map == null || map.size() ==  0){
                assertFail(msg);
            }
        }
    }

    /**
     * 断言错误
     */
    private static void assertFail() {
        ResponseBodyWrapper.failSysException();
    }

    /**
     * 断言错误，带错误信息
     */
    private static void assertFail(String msg) {
        ResponseBodyWrapper.failException(msg);
    }

}
