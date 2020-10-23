package com.heroland.competition.controller;

import com.anycommon.cache.service.RedisService;
//import com.anycommon.logger.annotation.CommonLogger;
import com.crossoverjie.cim.client.service.ImMessageServerService;
import com.crossoverjie.cim.client.vo.req.BaseSenderDTO;
import com.heroland.competition.common.constants.HeroLandRedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import com.spreada.utils.chinese.ZHConverter;

@Controller
public class HealthCloudHomeController {

    private final static Logger logger = LoggerFactory.getLogger(HealthCloudHomeController.class);


    @Resource
    private ImMessageServerService imMessageServerService;

    @Resource
    private RedisService redisService;
    @RequestMapping(value ={"/","/home"} )
//    @CommonLogger(name = "home 方法")
    public String home(HttpServletRequest request,String orgCode){

        /**
         * {"inviteId":"2718467022236156294001","opponentId":"2",
         * "opponentLevel":"ADVERSITY_HERO",
         *
         * "opponentStartTime":1596290248665,
         * "primaryRedisKey":"28427184670222361562940012",
         * "questionId":"4","topicId":"28","topicName":"wangkai001"}
         *
         * topicId + questionId + inviteId + opponentId;
         */
        redisService.del(HeroLandRedisConstants.COMPETITION+"28"+"4"+"2718467022236156294001"+"2");
        return "/res/index.html";

    }
    @RequestMapping(value ={"/heroland/tests"} )
//    @CommonLogger(name = "home 方法")
    @ResponseBody
    public Object test(HttpServletRequest request,String sendId,String addId) throws Exception {

        /**
         * {"inviteId":"2718467022236156294001","opponentId":"2",
         * "opponentLevel":"ADVERSITY_HERO",
         *
         * "opponentStartTime":1596290248665,
         * "primaryRedisKey":"28427184670222361562940012",
         * "questionId":"4","topicId":"28","topicName":"wangkai001"}
         *
         * topicId + questionId + inviteId + opponentId;
         */
        BaseSenderDTO baseSenderDTO = new BaseSenderDTO();
        baseSenderDTO.setAddresseeId(addId);
        baseSenderDTO.setSenderId(sendId);

        return imMessageServerService.sendOne2One("sss", baseSenderDTO);

    }


//        public static void main(String[] args) {
//            ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
//            String simplifiedStr = converter.convert("鄧麗君");
//            System.out.println(simplifiedStr);
//
//        System.out.println("***************");
//    }



}
