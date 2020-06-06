package com.heroland.competition.filter;


import com.alibaba.fastjson.JSON;
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


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //静态资源取消验证
        String uri = req.getRequestURI();

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
