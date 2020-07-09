package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dp.HerolandSkuDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.HerolandOrderQO;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.domain.qo.HerolandSkuQO;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.domain.request.HerolandSkuAddRequest;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 宝石管理
 */
@RestController
@RequestMapping("/heroland/diamond")
public class HeroLandDiamondController {

    @Resource
    private HerolandDiamondService herolandDiamondService;


    /**
     * 创建规格
     * @param
     * @return
     */
    @RequestMapping(value = "/create", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> create(@RequestBody HerolandSkuAddRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandSkuDP herolandSkuDP = new HerolandSkuDP();
        BeanUtils.copyProperties(request,herolandSkuDP);
        herolandDiamondService.createDiamondSku(herolandSkuDP);
        result.setData(true);
        return result;
    }

    /**
     * 获取宝石规格记录
     * @param qo
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandSkuDP>> listSku(@RequestBody HerolandSkuQO qo) {
        ResponseBody<List<HerolandSkuDP>> result = new ResponseBody<>();
        List<HerolandSkuDP> dps = herolandDiamondService.listSku(qo);
        result.setData(dps);
        return result;
    }

    /**
     * 更新某一个规格
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> prePay(@RequestBody HerolandSkuAddRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandSkuDP herolandSkuDP = new HerolandSkuDP();
        BeanUtils.copyProperties(request,herolandSkuDP);
        herolandDiamondService.updateDiamondSku(herolandSkuDP);
        result.setData(true);
        return result;
    }

    /**
     * 删除某一个规格
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> delete(@RequestParam("id") Long id){
        ResponseBody<Boolean> result = new ResponseBody<>();
        herolandDiamondService.deleteById(id);
        result.setData(true);
        return result;
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandSkuDP> get(@RequestParam("id") Long id){
        ResponseBody<HerolandSkuDP> result = new ResponseBody<>();
        HerolandSkuDP byId = herolandDiamondService.getById(id);
        result.setData(byId);
        return result;
    }


}
