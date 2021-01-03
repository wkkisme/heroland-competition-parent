package com.heroland.competition.filter;


import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.context.UserHolder;
import com.platform.sso.context.UserHolderContext;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangkai
 */
@Component
public class RouteCheckFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RouteCheckFilter.class);


    private String questionnaireIndexHtml;

    private String mainIndexHtml;

    private String docHtml;


    private String kpiHtml;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //静态资源取消验证
        try {
            String sessionId = CookieUtils.getSessionId(req);
            if (StringUtils.isNotBlank(sessionId)) {
                RpcResult<PlatformSysUserDP> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryCurrent(sessionId);
                PlatformSysUserDP data = platformSysUserDPRpcResult.getData();
                UserHolder userHolder = new UserHolder();
                userHolder.setOrgCode(data.getOrgCode());
                userHolder.setUserCode(data.getUserId());
                userHolder.setUserName(data.getUserName());
                UserHolderContext.setUser(userHolder);
            }
        } catch (Exception e) {
            log.error("获取user失败",e);
        }

        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }


    public String getQuestionnaireIndexHtml() {
        return questionnaireIndexHtml;
    }

    public void setQuestionnaireIndexHtml(String questionnaireIndexHtml) {
        this.questionnaireIndexHtml = questionnaireIndexHtml;
    }

    public String getKpiHtml() {
        return kpiHtml;
    }

    public void setKpiHtml(String kpiHtml) {
        this.kpiHtml = kpiHtml;
    }

    public String getMainIndexHtml() {
        return mainIndexHtml;
    }

    public void setMainIndexHtml(String mainIndexHtml) {
        this.mainIndexHtml = mainIndexHtml;
    }

    public String getDocHtml() {
        return docHtml;
    }

    public void setDocHtml(String docHtml) {
        this.docHtml = docHtml;
    }
}
