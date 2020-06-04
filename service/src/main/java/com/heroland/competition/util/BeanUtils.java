package com.heroland.competition.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Bean工具
 *
 * @author Eason@bianque
 * @date 2019/02/22
 **/
public class BeanUtils {

    public static <T> List<T> convertListData(List sources, Class targetClass) {
        try {

            if (sources == null || sources.size() == 0) {
                return new ArrayList<>();
            }

            List<Object> result = new ArrayList();
            Iterator iterator = sources.iterator();

            while (iterator.hasNext()) {
                Object source = iterator.next();
                Object target = targetClass.newInstance();
                org.springframework.beans.BeanUtils.copyProperties(source, target);
                result.add(target);
            }

            return (List<T>) result;
        } catch (Exception e) {
            return null;
        }
    }


    public static <T> T convertData(Object source, Class targetClass) {
        try {

            if (source == null || source == "") {
                return (T) targetClass.newInstance();
            }

            Object target = targetClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            return (T) target;
        } catch (Exception e) {
            return null;
        }
    }
}
