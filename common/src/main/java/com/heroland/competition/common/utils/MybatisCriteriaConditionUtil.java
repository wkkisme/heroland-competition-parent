package com.heroland.competition.common.utils;


import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * bqhealth-cloud
 * mybatis 生成工具查询条件封装
 *
 * @author wangkai
 * @date 2020/3/3
 */

public class MybatisCriteriaConditionUtil {

    private static final Logger logger = LoggerFactory.getLogger(MybatisCriteriaConditionUtil.class);

    /**
     * 创建查询参数
     *
     * @param criteria Criteria
     * @param qo       定义qo查询对象
     * @param <T>      Criteria
     * @param <Q>      qo
     * @return Criteria
     */
    public static <T, Q> T createCriteria(T criteria, Q qo) {
        Field[] declaredFields = qo.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            MybatisCriteriaAnnotation annotation = declaredField.getAnnotation(MybatisCriteriaAnnotation.class);
            if (annotation != null) {
                declaredField.setAccessible(true);
                switch (annotation.method()) {

                    /*
                      and equal
                     */
                    case AND_EQUAL_TO:
                        try {
                            Object param = declaredField.get(qo);
                            if (param != null) {
                                if (param instanceof String && StringUtils.isBlank((String) param)) {
                                    continue;
                                }

                                criteria.getClass().getSuperclass().getMethod(MybatisCriteriaMethodEnum.AND_EQUAL_TO.getPreMethodName() +
                                        captureName(declaredField.getName()) + MybatisCriteriaMethodEnum.AND_EQUAL_TO.getSufMethodName(), declaredField.getType())
                                        .invoke(criteria, param);
                            }

                        } catch (Exception e) {
                            logger.error("", e);
                        }
                        break;
                    /*

                      and like

                     */
                    case AND_LIKE:

                        try {
                            Object param = declaredField.get(qo);
                            if (param != null) {
                                if (param instanceof String && StringUtils.isBlank((String) param)) {
                                    continue;
                                }

                                criteria.getClass().getSuperclass().getMethod(MybatisCriteriaMethodEnum.AND_LIKE.getPreMethodName() +
                                        captureName(declaredField.getName()) + MybatisCriteriaMethodEnum.AND_LIKE.getSufMethodName(), declaredField.getType())
                                        .invoke(criteria, param+"%");
                            }

                        } catch (Exception e) {
                            logger.error("", e);
                        }
                        break;
                    case AND_GREATER_THAN_OR_EQUAL_TO:

                        try {
                            Object param = declaredField.get(qo);
                            if (param != null) {
                                if (param instanceof String && StringUtils.isBlank((String) param)) {
                                    continue;
                                }

                                criteria.getClass().getSuperclass().getMethod(MybatisCriteriaMethodEnum.AND_GREATER_THAN_OR_EQUAL_TO.getPreMethodName() +
                                        captureName(declaredField.getName()) + MybatisCriteriaMethodEnum.AND_GREATER_THAN_OR_EQUAL_TO.getSufMethodName(), declaredField.getType())
                                        .invoke(criteria, param);
                            }

                        } catch (Exception e) {
                            logger.error("", e);
                        }
                        break;
                    default:
                        break;
                }
            }
        }


        return criteria;
    }


    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
