package com.heroland.competition.dal.pojo;

public class HeroLandStatisticsTotalAll extends HeroLandStatisticsTotal {
    private Long rank;
    private Long rightCount;

    public Long getRightCount() {
        return rightCount;
    }

    public void setRightCount(Long rightCount) {
        this.rightCount = rightCount;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
