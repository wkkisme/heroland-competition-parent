package com.heroland.competition.util;


import org.elasticsearch.common.cache.Cache;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * bqhealth-cloud
 *
 * @author wangkai
 * @date 2020/5/27
 */

public class CheckRepUtil {

    /**
     * @param cache 缓存
     * @param wait 等待去重数据
     * @param fieldNames 去重字段 多个字段请按顺序传入
     * @param <T>
     * @return
     */
    public static <T> List<T> deduplicate( Cache<Object, Object> cache,List<T> wait,String... fieldNames)  {
        if (cache ==null || wait ==null || fieldNames == null){
            return wait;
        }
        Iterator<T> iterator = wait.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            try {
                StringBuilder fieldValue= new StringBuilder();
                for (String fieldName : fieldNames) {
                    Field declaredField = next.getClass().getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    fieldValue.append(declaredField.get(next));
                }

                if (cache.get(fieldValue.toString()) != null){
                    iterator.remove();
                }else {
                    cache.put(fieldValue.toString(),next);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return wait;
    }

}
