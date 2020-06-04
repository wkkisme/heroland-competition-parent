package com.heroland.competition.util;

import com.alijk.bq.constant.RespMsgEnum;
import com.alijk.bq.result.ResponseWrapper;
import com.heroland.competition.competition.common.utils.BaseResultUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Eason@bianque
 * @date 2019/02/22
 **/
public class ResultUtils {

    public static ResponseWrapper errorResponseWrapper() {

        return errorResponseWrapper(null);
    }

    public static ResponseWrapper errorResponseWrapper(String message) {
        ResponseWrapper<String> wrapper = new ResponseWrapper<>();
        wrapper.setErrCode(RespMsgEnum.BQ_ERROR_PARAME.getErrorCode());
        wrapper.setSuccess(false);
        if (StringUtils.isNotBlank(message)) {
            wrapper.setErrMsg(message);
        } else {
            wrapper.setErrMsg(RespMsgEnum.BQ_ERROR_PARAME.getErrorMessage());
        }
        return wrapper;
    }

    public static ResponseWrapper emptyResponseWrapper() {
        return BaseResultUtils.buildData(RespMsgEnum.BQ_EMPTY_PARAME);
    }


    public static <T> ResponseWrapper<List<T>> convertResponseWrapper(ResponseWrapper wrapper, Class targetClass) {

        ResponseWrapper<List<T>> response = new ResponseWrapper();

        response.setSuccess(wrapper.isSuccess());
        response.setErrMsg(wrapper.getErrMsg());
        response.setErrCode(wrapper.getErrCode());
        response.setPage(wrapper.getPage());

        if(wrapper.getData() == null){
            return response;
        }

        if (wrapper.getData() instanceof List) {
            List datas = (List) wrapper.getData();
            response.setData(BeanUtils.convertListData(datas, targetClass));
            return response;
        }
        return response;
    }
}
