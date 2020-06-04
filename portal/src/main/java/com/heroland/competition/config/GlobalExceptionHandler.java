package com.heroland.competition.config;

import com.alijk.bq.constant.RespMsgEnum;
import com.alijk.bq.exception.AppSystemException;
import com.alijk.bq.result.ResponseWrapper;
import com.alijk.bqhealth.sso.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangkai
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 声明要捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseWrapper defaultExceptionHandler(HttpServletRequest request, Exception e) {
         ResponseWrapper<Object> errorResult = new ResponseWrapper<>();
        if (e instanceof PermissionException || e instanceof AppSystemException) {
            errorResult.setErrMsg(e.getMessage());
            errorResult.setSuccess(false);
            errorResult.setErrCode("2000");
        }else {
            errorResult.setErrorMessage(RespMsgEnum.BQ_SYSTEM_ERROR);
        }
       logger.error("yichang",e);
        return errorResult;
    }

}
