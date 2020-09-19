package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.CompetitionStatusEnum;
import com.heroland.competition.common.enums.RedisRocketmqConstant;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.*;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.heroland.competition.common.enums.Constants.CommandReqType.STOP_ANSWER_QUESTIONS;

/**
 * 對賽規則，要麼12題對賽完結束，
 * 要麼連續戰勝對手4場結束，中間不能由和，
 * 要4題連勝就立刻終止對賽，並跳轉至勝負界面。
 * （注意：不管負方有沒有完成答題，也不管勝方做了多少題。）
 * <p>
 * 每題最多2min，每題獨立計時，按選擇順序出題。
 *
 * @author mac
 */
@Service
public class HeroLandSchoolCompetitionImpl implements HeroLandCompetitionService {

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private RedisService redisService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;


    private static final String selfSchoolKey = "competition_school_self_key";

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     *
     * @return
     */
    @Override
    public Integer getType() {
        return CompetitionEnum.SCHOOL.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP record) {
        //  判断当前人连续答题次数 连续答对4题
        ResponseBody<HeroLandCompetitionRecordDP> competitionRecordByRecordId = heroLandCompetitionRecordService.getCompetitionRecordByRecordId(new HeroLandCompetitionRecordQO());
        if (competitionRecordByRecordId.getData() == null) {
            return ResponseBodyWrapper.fail("比赛不存在", "40000");
        }
        if (competitionRecordByRecordId.getData().getStatus().equals(CompetitionStatusEnum.FINISH.getStatus())) {
            return ResponseBodyWrapper.fail("比赛已经结束", "40001");
        }
        boolean lock = redisService.setNx("competition_school_answer_key:" + record.getOpponentId() + record.getInviteId() + record.getTopicId(), record, "P1H");
        if (record.getAnswerType() == 0) {
            if (lock) {
                if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                    record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                    record.setAddresseeId(record.getOpponentId());
                } else {
                    record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                    record.setAddresseeId(record.getInviteId());
                }
                record.setSenderId(record.getUserId());
                record.setOpponentEndTime(new Date());
                record.setInviteEndTime(new Date());
                record.setStatus(CompetitionStatusEnum.FINISH.getStatus());
                //通知
                record.setType(STOP_ANSWER_QUESTIONS.getCode());
                rocketMQTemplate.syncSend(RedisRocketmqConstant.IM_SINGLE, JSON.toJSONString(record));
            }

        } else {
            // 否则就是答12道题的提交答案
            List<HeroLandQuestionRecordDetailDP> dps = heroLandQuestionService.judgeQuestionResult(record.getDetails());
            // 答对题数
            long rightCount = dps.parallelStream().map(HeroLandQuestionRecordDetailDP::getCorrectAnswer).count();
            // 如果是先答题的人
            if (lock) {
                redisService.set("competition_answer_right_count:" + getType() + record.getUserId(), rightCount);
                if (rightCount == record.getDetails().size()) {
                    if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                        record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        record.setAddresseeId(record.getOpponentId());
                        record.setOpponentEndTime(new Date());
                    } else {
                        record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                        record.setAddresseeId(record.getInviteId());
                        record.setInviteEndTime(new Date());
                    }
                }
                //通知
                record.setType(STOP_ANSWER_QUESTIONS.getCode());
                rocketMQTemplate.syncSend(RedisRocketmqConstant.IM_SINGLE, JSON.toJSONString(record));
            } else {
                record.setStatus(CompetitionStatusEnum.FINISH.getStatus());
                Long otherRightCount;
                if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {

                    otherRightCount = (Long) redisService.get("competition_answer_right_count:" + getType() + record.getInviteId());

                } else {
                    otherRightCount = (Long) redisService.get("competition_answer_right_count:" + getType() + record.getOpponentId());
                }
                if (rightCount > otherRightCount){
                    if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                        record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        record.setAddresseeId(record.getOpponentId());
                        record.setOpponentEndTime(new Date());
                    } else {
                        record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                        record.setAddresseeId(record.getInviteId());
                        record.setInviteEndTime(new Date());
                    }
                }else {
                    if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                        record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                        record.setAddresseeId(record.getOpponentId());
                    } else {
                        record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        record.setAddresseeId(record.getInviteId());
                    }
                }
            }
        }
        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
        heroLandCompetitionRecordService.updateCompetitionRecord(record);
        return ResponseBodyWrapper.successWrapper(record);
    }
}
