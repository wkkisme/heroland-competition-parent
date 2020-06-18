package com.heroland.competition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HealthCloudHomeController {

    private final static Logger logger = LoggerFactory.getLogger(HealthCloudHomeController.class);

    @RequestMapping(value ={"/","/home"} )
    public String home(HttpServletRequest request,String orgCode){
        return "/index.html";

    }


}
