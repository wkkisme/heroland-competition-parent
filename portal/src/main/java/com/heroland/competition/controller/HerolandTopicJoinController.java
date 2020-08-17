package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.HerolandTopicJoinStatisticsDto;
import com.heroland.competition.domain.request.HerolandTopicJoinRequest;
import com.heroland.competition.domain.request.HerolandTopicJoinStatisticsRequest;
import com.heroland.competition.service.HerolandTopicJoinUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author smjyouzan
 * @date 2020/8/17
 * 比赛参加
 */
@RestController
@RequestMapping("/heroland/topic")
public class HerolandTopicJoinController {

    @Resource
    private HerolandTopicJoinUserService herolandTopicJoinUserService;

    /**
     * 报名参赛
     * @param request
     * @return
     */
    @RequestMapping("/toJoin")
    public ResponseBody<Boolean> join(@RequestBody HerolandTopicJoinRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandTopicJoinUserDP dp = BeanCopyUtils.copyByJSON(request, HerolandTopicJoinUserDP.class);
        result.setData(herolandTopicJoinUserService.join(dp));
        return result;
    }

    /**
     * 取消参赛
     * @param request
     * @return
     */
    @RequestMapping("/toCacel")
    public ResponseBody<Boolean> cancel(@RequestBody HerolandTopicJoinRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandTopicJoinUserDP dp = BeanCopyUtils.copyByJSON(request, HerolandTopicJoinUserDP.class);
        result.setData(herolandTopicJoinUserService.cancel(dp));
        return result;
    }

    /**
     * 统计比赛的人数
     * @param request
     * @return
     */
    @RequestMapping("/statistics")
    public ResponseBody<HerolandTopicJoinStatisticsDto> statistics(@RequestBody HerolandTopicJoinStatisticsRequest request) {
        ResponseBody<HerolandTopicJoinStatisticsDto> result = new ResponseBody<>();
        HerolandTopicJoinUserDP dp = BeanCopyUtils.copyByJSON(request, HerolandTopicJoinUserDP.class);
        result.setData(herolandTopicJoinUserService.statistics(dp));
        return result;
    }



}
