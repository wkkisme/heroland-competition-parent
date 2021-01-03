package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.dal.pojo.HerolandBanner;
import com.heroland.competition.domain.qo.HerolandBannerQO;
import com.heroland.competition.service.banner.HeroLandBannerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图
 *  * @author  wangkai
 *  * @date 2020-07-29
 */
@RestController
@RequestMapping("/heroland/banner")
public class HeroLandBannerController {

    @Resource
    private HeroLandBannerService heroLandBannerService;


    /**
     * 新增轮播图
     * @param herolandBanner
     * @return
     */
    @RequestMapping("/save")
    public ResponseBody<Boolean> save(HerolandBanner herolandBanner){
        return heroLandBannerService.save(herolandBanner);
    }

    /**
     * 删除轮播图
     * @param herolandBanner
     * @return
     */
    @RequestMapping("/delete")
    public ResponseBody<Boolean> delete(HerolandBanner herolandBanner){
        return heroLandBannerService.delete(herolandBanner);
    }

    /**
     * 更新轮播图
     * @param herolandBanner
     * @return
     */
    @RequestMapping("/update")
    public ResponseBody<Boolean> update(HerolandBanner herolandBanner){
        return heroLandBannerService.update(herolandBanner);
    }

    /**
     * 获取
     * @param herolandBanner
     * @return
     */
    @RequestMapping("/getList")
    public ResponseBody<List<HerolandBanner>> get(HerolandBannerQO herolandBanner){
        return heroLandBannerService.get(herolandBanner);
    }
}
