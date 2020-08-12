package com.heroland.competition.controller;


import cn.hutool.core.util.StrUtil;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.common.UserStatusDP;
import com.anycommon.response.constant.UserStatusEnum;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.factory.CompetitionTopicFactory;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 答题相关
 *
 * @author wangkai
 * @date 2020/6/30
 */

@RestController
@RequestMapping("/heroland/competition")
public class HeroLandCompetitionController {

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Value("${answer.SyncTime}")
    private Long syncTime;

    @Value("${answer.examTime}")
    private Long examTime;

    /**
     * 答题
     * @param dp
     * @param request
     * @return
     */
    @PostMapping("/doAnswer")
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(@RequestBody HeroLandCompetitionRecordDP dp, HttpServletRequest request) {
        dp.setUserId(platformSsoUserServiceFacade.queryCurrent(CookieUtils.getSessionId(request)).getData().getUserId());
        ResponseBody<HeroLandCompetitionRecordDP> result = CompetitionTopicFactory.get(dp.getTopicType()).doAnswer(dp);
        try {
            // 通知所有人 可以被邀请了
            if (result.isSuccess()) {
                UserStatusDP userStatusDP = new UserStatusDP();
                userStatusDP.setTopicId(dp.getTopicId());
                userStatusDP.setStatus(UserStatusEnum.ONLINE.getStatus());
                userStatusDP.setUserId(dp.getUserId());
                userStatusDP.setOrgCode(dp.getOrgCode());
                userStatusDP.setUserStatus(UserStatusEnum.ONLINE.getStatus());
                rocketMQTemplate.syncSend("IM_LINE:CLUSTER", userStatusDP.toJSONString());
            }
        } catch (Exception ignored) {
        }
        return result;
    }

//    @PostMapping("/score/calculate/{userId}")
//    public ResponseBody<HeroLandCalculatorResultDP> calculateScore(@PathVariable("userId") String userId,
//                                                                   @RequestParam("competitionRecordId") String competitionRecordId) {
//        HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = new HeroLandCompetitionRecordDP();
//        heroLandCompetitionRecordDP.setRecordId(competitionRecordId);
//        HeroLandCalculatorResultDP calculate = heroLandCalculatorService.calculate(heroLandCompetitionRecordDP, userId);
//        return ResponseBodyWrapper.successWrapper(calculate);
//    }

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

    @PostMapping("/addCompetitionRecord")
    public ResponseBody<String> addCompetitionRecord(@RequestBody HeroLandCompetitionRecordDP dp) {
        return heroLandCompetitionRecordService.addCompetitionRecord(dp);
    }

    @GetMapping("/getCompetitionRecordById")
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordById(@RequestParam String competitionRecordId) {
        HeroLandCompetitionRecordQO qo = new HeroLandCompetitionRecordQO();
        qo.setRecordId(competitionRecordId);
        return heroLandCompetitionRecordService.getCompetitionRecordByRecordId(qo);
    }

    @GetMapping("/getCompetitionRecords")
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecords(@RequestBody HeroLandCompetitionRecordQO qo) {

        return heroLandCompetitionRecordService.getCompetitionRecords(qo);
    }
}
