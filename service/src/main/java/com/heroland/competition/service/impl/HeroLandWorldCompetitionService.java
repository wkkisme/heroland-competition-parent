package com.heroland.competition.service.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailMapper;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.domain.request.HeroLandTopicPageRequest;
import com.heroland.competition.service.HeroLandCompetitionService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.heroland.competition.service.HeroLandQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HeroLandWorldCompetitionService implements HeroLandCompetitionService {
    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    @Resource
    private HeroLandQuestionRecordDetailMapper heroLandQuestionRecordDetailMapper;

    @Resource
    private RedisService redisService;

    private static final String questionKey = "competition_question_world_key:";

    @Override
    public Integer getType() {
        return CompetitionEnum.WORLD.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP record) {
        record.worldCheck(redisService);
        HeroLandTopicPageRequest heroLandTopicPageRequest = new HeroLandTopicPageRequest();
        heroLandTopicPageRequest.setTopicId(Long.valueOf(record.getTopicId()));
        HeroLandTopicDto topic = heroLandQuestionService.getTopic(heroLandTopicPageRequest);
        if (topic == null || topic.getRegisterCount() == null) {
            return ResponseBodyWrapper.fail("比赛为空,或者报名人数为空！", "5000");
        }

        List<HeroLandQuestionRecordDetailDP> dps = heroLandQuestionService.judgeQuestionResult(record.getDetails());

        dps.forEach(v -> {
            if (v.getCorrectAnswer() == null){
                v.setCorrectAnswer(false);
            }
            // 答对才进入
            if (v.getCorrectAnswer()) {
                Long rank = redisService.incr(questionKey + record.getTopicId() + record.getDetails().get(0).getId(), 1);
                redisService.expire(questionKey + record.getTopicId() + record.getDetails().get(0).getId(), 60 * 60 * 24);
                // 第一个进入答此题的人
                v.setScore(Math.toIntExact(topic.getCountLimit() - (rank - 1)));

            } else {
                v.setScore(0);
            }
        });
        record.setRecordId(IDGenerateUtils.getIdByRandom(IDGenerateUtils.ModelEnum.DEFAULT) + "");
        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
        return ResponseBodyWrapper.successWrapper(record);
    }


    private int calculateScore(String userId, String topicId, Long questionId) {
        return heroLandQuestionRecordDetailMapper.countCorrectAnswer(topicId, questionId, userId);
    }
}
