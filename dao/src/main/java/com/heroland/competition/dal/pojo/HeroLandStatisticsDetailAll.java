package com.heroland.competition.dal.pojo;

public class HeroLandStatisticsDetailAll extends HeroLandStatisticsDetail {
    /**
     * 校排名
     */
    private Long rank;
    private Long rightCount;

    /**
     * 班级排名
     */
    private Long classRank;

    private String topicId;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Long getClassRank() {
        return classRank;
    }

    public void setClassRank(Long classRank) {
        this.classRank = classRank;
    }

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
