package com.heroland.competition.controller;

import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HealthCloudHomeController {

    private final static Logger logger = LoggerFactory.getLogger(HealthCloudHomeController.class);

    @RequestMapping(value ={"/","/home"} )
    public String home(HttpServletRequest request,String orgCode){
        Long id = TinyId.nextId("test");
        List<Long> ids = TinyId.nextId("test", 10);
        return "/res/index.html";

    }


}
