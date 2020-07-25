package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.util.Objects;

@Data
public class HerolandTopicQuestion extends BaseDO {

    private Long questionId;

    private Long topicId;

    private Long chapterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HerolandTopicQuestion that = (HerolandTopicQuestion) o;
        return Objects.equals(questionId, that.questionId) &&
                Objects.equals(topicId, that.topicId) &&
                Objects.equals(chapterId, that.chapterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, topicId, chapterId);
    }
}