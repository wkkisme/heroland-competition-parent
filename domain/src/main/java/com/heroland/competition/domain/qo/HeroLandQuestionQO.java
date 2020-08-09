package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeroLandQuestionQO extends BaseQO {

    @MybatisCriteriaAnnotation
    private String topicId;

    @MybatisCriteriaAnnotation
    private String questionId;

    /**
     * 比赛记录id
     */
    @MybatisCriteriaAnnotation
    private String recordId;

    /**
     * 答题记录id
     */
    @MybatisCriteriaAnnotation
    private String recordDetailId;

    /**
     * 答题用户id
     */
    @MybatisCriteriaAnnotation
    private String userId;

    public HeroLandQuestionQO querySynCheck() {

        AssertUtils.notBlank(this.questionId);
        AssertUtils.notBlank(this.topicId);

        return this;
    }
}
