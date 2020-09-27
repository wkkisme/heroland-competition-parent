package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smjyouzan
 * @date 2020/9/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseResultForUserQO extends BaseQO implements Serializable {

    /**
     * 科目id
     */
    @NotNull
    private String courseCode;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;


}
