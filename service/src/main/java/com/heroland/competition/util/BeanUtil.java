package com.heroland.competition.util;

import com.alibaba.fastjson.JSON;
import com.alijk.bq.result.ResponseWrapper;
import com.alijk.bqhealth.cloud.dal.pojo.BaseDO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangkai
 */
public class BeanUtil {

    /**
     * 插入时bean 之间拷贝  ? ---> DO
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target
     * @throws Exception
     */
    public static <T extends BaseDO> T insertConversion(Object source, T target) throws Exception {
        conversion(source, target);
        target.beforeInsert();
        return target;
    }

    /**
     * 更新时bean 之间拷贝 ? ---> DO
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target 对象
     * @throws Exception
     */
    public static <T extends BaseDO> T updateConversion(Object source, T target) throws Exception {
        conversion(source, target);
        target.beforeUpdate();
        return target;
    }

    /**
     * bean  vo --> DO 之间拷贝
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target
     * @throws Exception
     */
    public static <T extends BaseDO> T conversion(Object source, T target) throws Exception {
        if (source == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * bean  vo --> DO 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static void copyProperties(Object source, Object target)  {

        BeanUtils.copyProperties(source, target);
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T, R> List<T> queryListConversion(List<R> source, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        for (Object s : source) {
            T t = target.newInstance();
            BeanUtils.copyProperties(s, t);
            result.add(t);
        }
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝 包装成ResponseWrapper
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T, R> ResponseWrapper<List<T>> queryWrapperListConversion(ResponseWrapper<List<R>> source, Class<T> target) throws Exception {
        List<T> resultList = new ArrayList<>();
        List<R> data = source.getData();
        if (!CollectionUtils.isEmpty(data)) {
            for (Object s : data) {
                T t = target.newInstance();
                BeanUtils.copyProperties(s, t);
                resultList.add(t);
            }
        }
        ResponseWrapper<List<T>> result = new ResponseWrapper<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(resultList);
        return result;
    }
 /**
     * 查询时list DO --> vo bean 之间拷贝 包装成ResponseWrapper
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T, R>  com.alijk.bqcommon.base.result.ResponseWrapper<List<T>> queryWrapperListConversion( com.alijk.bqcommon.base.result.ResponseWrapper<List<R>> source, Class<T> target) throws Exception {
        List<T> resultList = new ArrayList<>();
        List<R> data = source.getData();
        if (!CollectionUtils.isEmpty(data)) {
            for (Object s : data) {
                T t = target.newInstance();
                BeanUtils.copyProperties(s, t);
                resultList.add(t);
            }
        }
        com.alijk.bqcommon.base.result.ResponseWrapper<List<T>> result = new  com.alijk.bqcommon.base.result.ResponseWrapper<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(resultList);
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝 包装成ResponseWrapper
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T> ResponseWrapper<List<T>> queryWrapperListMapConversion(ResponseWrapper<List<Map>> source, Class<T> target) throws Exception {
        List<T> resultList = new ArrayList<>();
        List<Map> data = source.getData();
        if (!CollectionUtils.isEmpty(data)) {
            for (Object s : data) {
                T t = JSON.parseObject(JSON.toJSONString(s), target);
                resultList.add(t);
            }
        }
        ResponseWrapper<List<T>> result = new ResponseWrapper<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(resultList);
        return result;
    }

    /**
     * DO --> vo bean 之间拷贝 包装成ResponseWrapper
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static <T, R> ResponseWrapper<T> queryWrapperConversion(ResponseWrapper<R> source, T target) throws Exception {

        BeanUtils.copyProperties(source.getData(), target);
        ResponseWrapper<T> result = new ResponseWrapper<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(target);
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T extends BaseDO, R> List<T> mappingListConversion(List<R> source, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        for (Object s : source) {
            T t = target.newInstance();
            BeanUtils.copyProperties(s, t);
            t.beforeUpdate();
            result.add(t);
        }
        return result;
    }

    /**
     * 查询时list do --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static <T> T queryConversion(Object source, T target) throws Exception {

        if (source == null) {
            return null;
        }

        BeanUtils.copyProperties(source, target);
        return target;
    }

}
