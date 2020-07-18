package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandCoursePageRequest extends BaseQO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 年级上下
     */
    private Integer unit;

    /**
     * 科目
     */
    private String course;

    /**
     * 版本
     */
    private String edition;

    /**
     * 必修选修
     */
    private String editionType;

}
