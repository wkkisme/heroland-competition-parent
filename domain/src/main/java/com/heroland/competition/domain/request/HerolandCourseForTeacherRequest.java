package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/9/30
 */
@Data
public class HerolandCourseForTeacherRequest extends BaseQO implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 年级列表code
     */
    private List<String> gradeCodeList;

    /**
     * 班级列表code
     */
    private List<String> classCodeList;

}
