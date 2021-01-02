package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 科目完成情况查询
 *
 * @author wushuaiping
 * @date 2020/7/25 21:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseFinishStatisticQO extends BaseQO implements Serializable {

    /**
     * 当前用户id
     */
    private String userId;

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

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 有效时间
     */
    private Date validTime;
}
