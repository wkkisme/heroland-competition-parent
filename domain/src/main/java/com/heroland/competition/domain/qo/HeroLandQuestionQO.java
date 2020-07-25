package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeroLandQuestionQO extends BaseQO {

    @MybatisCriteriaAnnotation
    private Long topicId;

    @MybatisCriteriaAnnotation
    private Long questionId;

    /**
     * 比赛记录id
     */
    @MybatisCriteriaAnnotation
    private String competitionRecordId;

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
}
