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
import com.heroland.competition.service.questionBank.HeroLandQuestionBankService;
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

//    @Resource
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
//         herolandOrderService.createQuestion(orderDP);
        result.setData(true);
        return result;
    }


}
