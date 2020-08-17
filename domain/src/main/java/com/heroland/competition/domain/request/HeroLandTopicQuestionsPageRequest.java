package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeroLandTopicQuestionsPageRequest extends BaseQO implements Serializable {

    /**
     * 比赛组id
     */
    private List<Long> topicIds;
    /**
     * 题目id
     */
    private Long questionId;


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
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private String userId;

    private Integer topicType;

    private Integer type;

}
