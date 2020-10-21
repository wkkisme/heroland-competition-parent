package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @MybatisCriteriaAnnotation
    private String subjectCode;

    private Date startTime;

    private Date endTime;

    /**
     * 机构code
     */
    private String orgCode;

    /**
     * 年级code
     */
    private String gradeCode;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 科目code
     */
    private String courseCode;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    private Integer type;

    private Set<Long> questionIds;

    private Set<Long> topicIds;
    private Set<String> records;

    private Boolean history;


    public HeroLandQuestionQO querySynCheck() {

        AssertUtils.notBlank(this.questionId);
        AssertUtils.notBlank(this.topicId);

        return this;
    }
}
