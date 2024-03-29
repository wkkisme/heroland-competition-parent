package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HeroLandUserDepartmentDto extends BaseDO implements Serializable {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 机构code
     */
    private String departmentCode;

    /**
     * 机构类型
     * 学校|年级|班级
     *  SH 学校
     *  CA 班级
     *  GA 年级
     */
    private String departmentType;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 机构名称
     */
    private String departmentName;

    /**
     * heroland_user_class
     */
    private static final long serialVersionUID = 1L;


}