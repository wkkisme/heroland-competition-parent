package com.heroland.competition.controller;

import com.spreada.utils.chinese.ZHConverter;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @RequestMapping(value ={"/","/home"} )
    public String home(HttpServletRequest request,String orgCode){
        redisTemplate.opsForValue().set("1",3);
        Object o = redisTemplate.opsForValue().get("1");
        System.out.println(o);
//        Long id = TinyId.nextId("test");
//        List<Long> ids = TinyId.nextId("test", 10);
        return "/res/index.html";

    }


        public static void main(String[] args) {
            ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
            String simplifiedStr = converter.convert("鄧麗君");
            System.out.println(simplifiedStr);

        System.out.println("***************");
    }


}
