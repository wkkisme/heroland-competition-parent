package com.heroland.competition.controller;

import com.anycommon.cache.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
    public String home(HttpServletRequest request,String orgCode){
        redisService.set("1",3);
        Object o = redisService.get("1");
        System.out.println(o);
//        Long id = TinyId.nextId("test");
//        List<Long> ids = TinyId.nextId("test", 10);
        return "/res/index.html";

    }


}
