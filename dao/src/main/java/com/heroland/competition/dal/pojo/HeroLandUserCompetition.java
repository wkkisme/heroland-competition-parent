package com.heroland.competition.dal.pojo;

import java.util.List;

public class HeroLandUserCompetition extends HeroLandAccount{
    private List<HeroLandCompetitionRecord> records;

    public List<HeroLandCompetitionRecord> getRecords() {
        return records;
    }

    public void setRecords(List<HeroLandCompetitionRecord> records) {
        this.records = records;
    }
}
