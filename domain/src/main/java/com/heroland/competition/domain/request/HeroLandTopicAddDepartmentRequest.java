package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class HeroLandTopicAddDepartmentRequest implements Serializable {

    /**
     * 编辑时需要传
     */
    private Long id;

    /**
     * 赛事id
     */
    @NotNull
    private Long topicId;

    /**
     * 添加的学校科目相关的列表
     */
    List<HerolandTopicAddSchoolCourseRequest> schoolCourses;
}