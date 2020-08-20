package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/14
 */
@Data
public class UserDepartmentRequest implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 机构类型
     * 学校|年级|班级
     * SH 学校
     * CA 班级
     * GA年级
     */
    @NotNull
    private String departmentType;
}
