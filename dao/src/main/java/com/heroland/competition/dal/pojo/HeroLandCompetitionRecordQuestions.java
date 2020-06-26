package com.heroland.competition.dal.pojo;

import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;

import java.util.List;

public class HeroLandCompetitionRecordQuestions extends HeroLandCompetitionRecord {
    private List<HeroLandQuestionRecordDetail> details;


    public List<HeroLandQuestionRecordDetail> getDetails() {
        return details;
    }

    public void setDetails(List<HeroLandQuestionRecordDetail> details) {
        this.details = details;
    }
}
