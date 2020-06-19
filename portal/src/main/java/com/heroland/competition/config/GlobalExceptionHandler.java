package com.heroland.competition.config;

import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.ResponseBodyWrapper;
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
    public com.anycommon.response.common.ResponseBody defaultExceptionHandler(HttpServletRequest request, Exception e) {
        logger.error("异常信息：",e);
        if (e instanceof AppSystemException){
            ResponseBodyWrapper.fail(e.getMessage(),"50000");
        }
        return ResponseBodyWrapper.fail(ErrMsgEnum.SERVER_ERROR);
    }

}
