package com.heroland.competition.common.utils;

import com.alijk.bq.constant.RespMsgEnum;
import org.apache.commons.lang3.StringUtils;

import com.alijk.bq.result.ResponseWrapper;
import java.util.List;

/**
 * @author: kui.zhouk
 * @date: 2018-11-09
 */
public class BaseResultUtils {

    /*public static <T> BaseResult<T> buildData(T data){
        BaseResult<T> result = new BaseResult<T>();
        result.setCode(DataQualityConstants.SUCCESS_STATE);
        result.setData(data);
        return result;
    }*/

    public static ResponseWrapper<String> judge(boolean repeat, boolean sheetFlag, int count, List<String> sheetNames) {
        ResponseWrapper<String> result = new ResponseWrapper<>();
        /*如果存在重复数据 返回重复结果条数 sheet格式正确*/
        if (repeat && !sheetFlag) {
            result.setErrCode("200");
            result.setErrMsg("导入数据成功");
            return result;
        }
        /*sheet格式错误 数据不重复*/
        if (!repeat && sheetFlag) {
            result.setSuccess(false);
            result.setErrCode(RespMsgEnum.BQ_INSERT_FAILED.getErrorCode());
            result.setErrMsg("部分数据导入成功，错误的工作表" + sheetNames + ",请上传正确的格式");
            return result;
        }
        /*sheet格式错误 数据重复*/
        if (repeat && sheetFlag) {
            result.setSuccess(false);
            result.setErrCode(RespMsgEnum.BQ_INSERT_FAILED.getErrorCode());
            result.setErrMsg("导入数据失败，错误工作表" + sheetNames);
            return result;
        }
        return new ResponseWrapper<>();
    }

    public static ResponseWrapper buildData(RespMsgEnum msgEnum) {
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(msgEnum.getErrorCode());
        result.setErrMsg(msgEnum.getErrorMessage());
        return result;
    }

    public static ResponseWrapper buildEmptyResult(String msg) {
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(RespMsgEnum.BQ_EMPTY_PARAME.getErrorCode());
        if (StringUtils.isBlank(msg)) {
            result.setErrMsg(RespMsgEnum.BQ_EMPTY_PARAME.getErrorMessage());
        } else {
            result.setErrMsg(msg);
        }
        return result;
    }
}
