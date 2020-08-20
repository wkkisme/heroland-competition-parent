package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/14
 */
@Data
public class UserClassRequest implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 查询的code
     * 具体是什么code要看departmentType；
     * 如果为空则默认查该userId下的所有code
     */
    private List<String> departmentCode;

    /**
     * * 机构类型
     *      * 学校|年级|班级
     *      * SH 学校
     *      * CA 班级
     *      * GA年级
     */
    private String departmentType;
}
