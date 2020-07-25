package com.heroland.competition.controller;

import com.anycommon.cache.service.RedisService;
import com.anycommon.logger.annotation.CommonLogger;
import com.spreada.utils.chinese.ZHConverter;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HealthCloudHomeController {

    private final static Logger logger = LoggerFactory.getLogger(HealthCloudHomeController.class);

    @Resource
    private RedisService redisService;
    @RequestMapping(value ={"/","/home"} )
    @CommonLogger(name = "home 方法")
    public String home(HttpServletRequest request,String orgCode){

//        Long id = TinyId.nextId("test");
//        List<Long> ids = TinyId.nextId("test", 10);
        return "/res/index.html";

    }


//        public static void main(String[] args) {
//            ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
//            String simplifiedStr = converter.convert("鄧麗君");
//            System.out.println(simplifiedStr);
//
//        System.out.println("***************");
//    }


}
