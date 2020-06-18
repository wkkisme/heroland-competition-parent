package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandTopicQuestion")
public class HeroLandTopicQuestion extends BaseDO implements Serializable {
    /**
     * 全局id
     */
    @ApiModelProperty(value="questionId全局id")
    private String questionId;

    /**
     * 题目组id
     */
    @ApiModelProperty(value="topicId题目组id")
    private String topicId;

    /**
     * heroland_topic_question
     */
    private static final long serialVersionUID = 1L;

    /**
     * 全局id
     * @return question_id 全局id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 全局id
     * @param questionId 全局id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    /**
     * 题目组id
     * @return topic_id 题目组id
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 题目组id
     * @param topicId 题目组id
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }
}