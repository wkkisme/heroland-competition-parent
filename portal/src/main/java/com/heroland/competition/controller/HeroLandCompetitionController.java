package com.heroland.competition.controller;


import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.service.HeroLandCompetitionService;
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

    @PostMapping("/doAnswer")
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(@RequestBody HeroLandCompetitionRecordDP dp) {

        return heroLandCompetitionService.doAnswer(dp);
    }

//    @GetMapping("/getSyncCompetitionRecord")
//    public ResponseBody<>
}
