package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.HerolandOrderQO;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.domain.request.HerolandQuestionBankRequest;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 题库
 */
@RestController
@RequestMapping("/heroland/quesBank")
public class HeroLandQuestionBankController {

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;

    @Resource
    private HerolandPayService herolandPayService;

    /**
     * 创建题目
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> createQuestion(@RequestBody HerolandQuestionBankRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
         herolandOrderService.createQuestion(orderDP);
        result.setData(true);
        return result;
    }

    /**
     * 获取充值记录
     * @param qo
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandOrderDP>> listOrder(@RequestBody HerolandOrderQueryQO qo) {
        ResponseBody<List<HerolandOrderDP>> result = new ResponseBody<>();

        List<HerolandOrderDP> herolandOrderDPS = herolandOrderService.listOrder(qo.getBuyerId(), qo.getStatus());
        result.setData(herolandOrderDPS);
        return result;
    }

    /**
     *
     * @param prePayQO
     * @return
     */
    @RequestMapping(value = "/prePay", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<PrePayDto> prePay(@RequestBody PrePayQO prePayQO){
        ResponseBody<PrePayDto> result = new ResponseBody<>();
        result.setData(herolandPayService.prePay(prePayQO));
        return result;
    }

//    @RequestMapping(value = "/prePay", produces = "application/json;charset=UTF-8")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public ResponseBody<PrePayDto> prePay(@RequestParam("payId") Long payId){
//        ResponseBody<PrePayDto> result = new ResponseBody<>();
//        PrePayQO prePayQO = new PrePayQO();
//        prePayQO.setPayId(payId);
//        result.setData(herolandPayService.prePay(prePayQO));
//        return result;
//    }

    @RequestMapping(value = "/id", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandPayDP> get(@RequestParam("id") Long id){
        ResponseBody<HerolandPayDP> result = new ResponseBody<>();
        result.setData(herolandPayService.getPayById(id));
        return result;
    }


}
