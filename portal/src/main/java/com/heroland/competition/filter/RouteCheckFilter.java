package com.heroland.competition.filter;


import com.alibaba.fastjson.JSON;
import com.alijk.bq.context.UserHolder;
import com.alijk.bq.context.UserHolderContext;
import com.alijk.bqcommon.cache.jedis.JedisTemplate;
import com.heroland.competition.competition.common.utils.ResponseWrite;
import com.alijk.bqhealth.sso.client.sso.constant.SsoClientConstants;
import com.alijk.bqhealth.sso.client.sso.model.SSOUserBO;
import com.alijk.bqhealth.sso.client.sso.util.CookieUtils;
import com.alijk.bqhealth.sso.facade.HealthCloudSsoServiceFacade;
import com.alijk.bqhealth.sso.facade.result.RpcResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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


    private String indexHtml;

    private String patientHtml;

    private String reportCenter;

    private String questionnaireIndexHtml;

    private String mainIndexHtml;

    private String docHtml;


    private static List<String> STATIC_RESOURCES = Arrays.asList("css", "js", "jpg", "png", "svg");
    private String serverHtml;

    private String kpiHtml;
	
    private String[] urls ={"/hcloud"};

    @Resource
    private JedisTemplate jedisTemplate;

    private HealthCloudSsoServiceFacade healthCloudSsoServiceFacade;

    private void setUser(HttpServletRequest request, String orgCode) {
        if (orgCode != null) {
            RpcResult<SSOUserBO> userinfo = healthCloudSsoServiceFacade.checkLoginBySessionId(CookieUtils.getValue(request, SsoClientConstants.SESSION_ID));
            if (userinfo != null && userinfo.getData() != null && userinfo.isSuccess()) {
//                userinfo.getData().setOrgCode(orgCode);
                log.info(JSON.toJSONString(userinfo));
                UserHolder ssoUserBO = new UserHolder();
                BeanUtils.copyProperties(userinfo.getData(), ssoUserBO);
                UserHolderContext.setUser(ssoUserBO);
                try {
                    jedisTemplate.setEx("data_sso_key" + ssoUserBO.getUserCode(), JSON.toJSONString(ssoUserBO), 60 * 30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setHealthCloudSsoServiceFacade(HealthCloudSsoServiceFacade healthCloudSsoServiceFacade) {
        this.healthCloudSsoServiceFacade = healthCloudSsoServiceFacade;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //静态资源取消验证
        String uri = req.getRequestURI();
        for (String url : urls) {
            if (uri.contains(url)) {
                ResponseWrite.write(resp, indexHtml);
                return;
            }
        }
        if(uri.contains("/hkpi")){
            /*String orgCode = request.getParameter("orgCode");
            if (orgCode != null) {
                setUser(req,orgCode );
            }*/
            ResponseWrite.write(resp,kpiHtml);
            return;
        }

        if (uri.contains("/patientindexs")) {

            ResponseWrite.write(resp, patientHtml);
            return;
        }

        if (uri.contains("/reportcenters")) {
            String orgCode = request.getParameter("orgCode");
            if (orgCode != null) {
                setUser(req, orgCode);
            }
            ResponseWrite.write(resp, reportCenter);
            return;
        }

        if (uri.contains("/questionnaire")) {
            String[] strings = StringUtils.split(uri, ".", 0);


            if (STATIC_RESOURCES.contains(strings.length >= 2 ? strings[strings.length - 1] : "")) {
                chain.doFilter(request, response);
                return;
            }
            //questionnaireIndexHtml.
            ResponseWrite.write(resp, questionnaireIndexHtml);
            return;
        }

        if (uri.contains("/hulianhutongservers")){
            String orgCode = request.getParameter("orgCode");
            if (orgCode != null) {
                setUser(req,orgCode );
            }
            ResponseWrite.write(resp,serverHtml);
            return;
        }
        if (uri.contains("/mainindexs")){
            String orgCode = request.getParameter("orgCode");
            if (orgCode != null) {
                setUser(req,orgCode );
            }
            ResponseWrite.write(resp,mainIndexHtml);
            return;
        }

        if (uri.contains("/docposts")){
            String orgCode = request.getParameter("orgCode");
            if (orgCode != null) {
                setUser(req,orgCode);
            }
            ResponseWrite.write(resp,docHtml);
            return;
        }
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }

    public void setReportCenter(String reportCenter) {
        this.reportCenter = reportCenter;
    }

    public void setPatientHtml(String patientHtml) {
        this.patientHtml = patientHtml;
    }

    public void setIndexHtml(String indexHtml) {
        this.indexHtml = indexHtml;
    }

    public String getQuestionnaireIndexHtml() {
        return questionnaireIndexHtml;
    }

    public void setQuestionnaireIndexHtml(String questionnaireIndexHtml) {
        this.questionnaireIndexHtml = questionnaireIndexHtml;
    }
    public void setServerHtml(String serverHtml) {
        this.serverHtml = serverHtml;
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
