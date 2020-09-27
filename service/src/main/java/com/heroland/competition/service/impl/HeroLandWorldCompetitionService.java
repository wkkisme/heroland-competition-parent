package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
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

    @Override
    public Integer getType() {
        return CompetitionEnum.WORLD.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP record) {
        record.worldCheck();

        List<HeroLandQuestionRecordDetailDP> dps = heroLandQuestionService.judgeQuestionResult(record.getDetails());
        int score = dps.size();

        dps.forEach(v -> {
            if (v.getCorrectAnswer() != null && v.getCorrectAnswer()) {
                v.setScore(score);
            } else {
                v.setScore(0);
            }
        });
        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
        return ResponseBodyWrapper.successWrapper(record);
    }
}
