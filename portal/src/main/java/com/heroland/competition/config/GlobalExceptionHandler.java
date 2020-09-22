package com.heroland.competition.config;

import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.platform.sso.client.sso.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

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

        String language = CookieUtils.getValue(request, "language");
        Locale locale = null;
//        if ("zh_TW".equalsIgnoreCase(language)){
//            locale = Locale.TRADITIONAL_CHINESE;
//        }else if ("en".equalsIgnoreCase(language)){
//            locale = Locale.US;
//        }else {
//            locale = Locale.SIMPLIFIED_CHINESE;
//        }
        locale = Locale.TRADITIONAL_CHINESE;
//        LocaleContextHolder.setLocale(locale);
        if (e instanceof AppSystemException){
//            String i18nStr = StringUtils.isBlank(Resources.getMessage(e.getMessage())) ? e.getMessage() : Resources.getMessage(e.getMessage());
//            return  ResponseBodyWrapper.fail(i18nStr,"50000");
            return  ResponseBodyWrapper.fail(e.getMessage(),"50000");
        }
        return ResponseBodyWrapper.fail(ErrMsgEnum.SERVER_ERROR);
    }

}
