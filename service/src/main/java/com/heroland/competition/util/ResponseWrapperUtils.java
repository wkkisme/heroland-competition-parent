package com.heroland.competition.util;

import com.alibaba.fastjson.JSON;
import com.alijk.bq.result.ResponseWrapper;

public class ResponseWrapperUtils {
    /**
     * 参数为空
     *
     * @return
     */
    public static ResponseWrapper resultEmpty(String ...args){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(ResponseConstants.EMPTY_PARAMS.getRetCode());

        StringBuilder sb = new StringBuilder();
        int length = args.length - 1;
        for(int i=0; i<length; i++){
            sb.append(args[i]).append(", ");
        }
        sb.append(args[length]);

        result.setErrMsg(ResponseConstants.EMPTY_PARAMS.getMsg() + ",请检查以下参数: " + sb.toString());
        return result;
    }

    /**
     * 参数为空
     *
     * @return
     */
    public static String resultEmptyStr(String ...args){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(ResponseConstants.EMPTY_PARAMS.getRetCode());

        StringBuilder sb = new StringBuilder();
        int length = args.length - 1;
        for(int i=0; i<length; i++){
            sb.append(args[i]).append(", ");
        }
        sb.append(args[length]);

        result.setErrMsg(ResponseConstants.EMPTY_PARAMS.getMsg() + ",请检查以下参数: " + sb.toString());
        return JSON.toJSONString(result);
    }

    /**
     * 错误信息封装
     *
     * @param responseConstants
     * @return
     */
    public static ResponseWrapper errMsg(ResponseConstants responseConstants){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(responseConstants.getRetCode());
        result.setErrMsg(responseConstants.getMsg());
        return result;
    }

    /**
     * 错误信息封装
     *
     * @param
     * @return
     */
    public static ResponseWrapper errMsg(String code, String msg){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    /**
     * 返回信息封装
     *
     * @param responseWrapper
     * @return
     */
    public static ResponseWrapper resultErrMsg(ResponseWrapper responseWrapper){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(responseWrapper.getErrCode());
        result.setErrMsg(responseWrapper.getErrMsg());
        return result;
    }

    /**
     * 返回信息封装
     *
     * @param data
     * @return
     */
    public static ResponseWrapper resultSuccessMsg(Object data){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(true);
        result.setErrCode(ResponseConstants.SUCCESS.getRetCode());
        result.setErrMsg(ResponseConstants.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }


    /**
     * 错误信息封装
     *
     * @param responseConstants
     * @return
     */
    public static String errMsgStr(ResponseConstants responseConstants){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(responseConstants.getRetCode());
        result.setErrMsg(responseConstants.getMsg());
        return JSON.toJSONString(result);
    }

    /**
     * 错误信息封装
     *
     * @param
     * @return
     */
    public static String errMsgStr(String code, String msg){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(false);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return JSON.toJSONString(result);
    }

    /**
     * 返回信息封装
     *
     * @param data
     * @return
     */
    public static String resultSuccessMsgStr(Object data){
        ResponseWrapper result = new ResponseWrapper();
        result.setSuccess(true);
        result.setErrCode(ResponseConstants.SUCCESS.getRetCode());
        result.setErrMsg(ResponseConstants.SUCCESS.getMsg());
        result.setData(data);
        return JSON.toJSONString(result);
    }
}
