package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangkai
 * @date 2020/7/5
 */
@Data
public class HeroLandClassManageQO  extends BaseQO implements Serializable {

    /**
     * 学校
     */
    private String orgCode;

    /**
     * 年级
     */
    private String gradeCode;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 班级code
     */
    private String className;

    /**
     * id
     */
    private Long id;

}
