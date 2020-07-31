package com.heroland.competition.controller;


import cn.hutool.core.util.StrUtil;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.service.HeroLandCompetitionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/30
 */

@RestController
@RequestMapping("/heroland/competition")
public class HeroLandCompetitionController {

    @Resource
    private HeroLandCompetitionService heroLandCompetitionService;

    @Value("${answer.SyncTime}")
    private Long syncTime;

    @Value("${answer.examTime}")
    private Long examTime;

    @PostMapping("/doAnswer")
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(@RequestBody HeroLandCompetitionRecordDP dp) {

        return heroLandCompetitionService.doAnswer(dp);
    }

    /**
     * @param topicType
     * @return
     */
    @GetMapping("/getAnswerTime")
    public ResponseBody<Long> getAnswerTime(@RequestParam String topicType) {
        if (StrUtil.equals(topicType, "0")) {
            return ResponseBodyWrapper.successWrapper(syncTime);
        }
        if (StrUtil.equals(topicType, "3")) {
            return ResponseBodyWrapper.successWrapper(examTime);
        }
        ResponseBodyWrapper.failParamException();
        return null;
    }
}
