package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/19
 */
@Data
public class HerolandTopicHeaderTeacherCanAssignQO extends BaseQO implements Serializable {

    /**
     *  用户id
     *  教师id
     *  如果非班主任，则返回空
     */
    @NotNull
    private String userId;

    /**
     * 赛事的时间状态
     * 正在进行的 DOING
     * 已经过期的 OVERDUE
     * 还未开始的 NOTSTART
     */
    private String topicState;

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
}
