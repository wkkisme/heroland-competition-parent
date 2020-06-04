package com.heroland.competition.controller;

import com.alibaba.fastjson.JSON;
import com.alijk.bq.context.UserHolder;
import com.alijk.bq.context.UserHolderContext;
import com.alijk.bqcommon.cache.jedis.JedisTemplate;
import com.alijk.bqhealth.sso.client.sso.constant.SsoClientConstants;
import com.alijk.bqhealth.sso.client.sso.model.SSOUserBO;
import com.alijk.bqhealth.sso.client.sso.util.CookieUtils;
import com.alijk.bqhealth.sso.facade.HealthCloudSsoServiceFacade;
import com.alijk.bqhealth.sso.facade.result.RpcResult;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HealthCloudHomeController {

    private final static Logger logger = LoggerFactory.getLogger(HealthCloudHomeController.class);

    @Reference(version = "${health.dubbo.version}",group = "bqhealth-cloud")
    private HealthCloudSsoServiceFacade healthCloudSsoServiceFacade;

    @Resource
    private JedisTemplate jedisTemplate;

    @RequestMapping(value ={"/","/home"} )
    public String home(HttpServletRequest request,String orgCode){
        setUser(request, orgCode);
        return "/res/index.html";

    }

    private void setUser(HttpServletRequest request, String orgCode) {
        if (orgCode != null ) {
            logger.info("跳转orgCode : ",orgCode);
            RpcResult<SSOUserBO> userinfo = healthCloudSsoServiceFacade.checkLoginBySessionId(CookieUtils.getValue(request, SsoClientConstants.SESSION_ID));
            if (userinfo != null && userinfo.getData() != null && userinfo.isSuccess()) {
                UserHolder ssoUserBO = new UserHolder();
                BeanUtils.copyProperties(userinfo.getData(), ssoUserBO);
                UserHolderContext.setUser(ssoUserBO);
                logger.info("跳转用户为 : ", JSON.toJSONString(ssoUserBO));
                jedisTemplate.setEx("data_sso_key" + ssoUserBO.getUserCode(), JSON.toJSONString(ssoUserBO), 60 * 30);
            }
        }
    }

    @RequestMapping("/index")
    public Object forWard(HttpServletRequest request, Model model,String orgCode){
        setUser(request, orgCode);
        return "/res/index.html";

    }

    @RequestMapping("/init")
    public String init(){

        return "/init/init.html";

    }
    @RequestMapping("/totask")
    public String task(){


        return "/task.html";
    }


    @RequestMapping("/patientdetails")
    public String patientDetail(HttpServletRequest request,RedirectAttributes attr, String idNum, String name, String mainIndexNum,String orgCode,String token){
        attr.addAttribute("idNum",idNum);
        attr.addAttribute("name",name);
        attr.addAttribute("mainIndexNum",mainIndexNum);
        attr.addAttribute("orgCode",orgCode);
        attr.addAttribute("token",token);

        setUser(request, orgCode);
        return "/patientdetail/index.html";
    }

    @RequestMapping("/patientindexs")
    public String patientIndex(HttpServletRequest request,String orgCode){

        setUser(request, orgCode);
        return "/patientindex/index.html";
    }

    @RequestMapping("/reportcenters")
    public String reportCenter(HttpServletRequest request,String orgCode){
        setUser(request, orgCode);
        return "/reportcenter/index.html";

    }

   @RequestMapping("/hulianhutongservers")
    public String hulianhutongserver(HttpServletRequest request,String orgCode){
        setUser(request, orgCode);
        return "/hulianhutongserver/index.html";

    }

   @RequestMapping("/mainindexs")
    public String mainIndex(HttpServletRequest request,String orgCode){
        setUser(request, orgCode);
        return "/mainindex/index.html";

    }

   @RequestMapping("/docposts")
    public String doc(HttpServletRequest request,String orgCode){
        setUser(request, orgCode);
        return "/docpost/index.html";

    }


}
