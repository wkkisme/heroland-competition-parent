package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/9/30
 */
@Data
public class SchoolCourseForTeacherDto implements Serializable {

    /**
     * key
     */
    private String key;

    /**
     * 名称
     */
    private String name;
}
