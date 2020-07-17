package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolEditRequest implements Serializable {

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 节点的详细名称
     */
    @NotNull
    private String name;

    /**
     * 某一节点的业务no，比如香港的学校有自己的编号
     */
    private String bizNo;

    /**
     * 业务的国际化表示，比如学校就是有自己的英文名称
     */
    private String bizI18N;

    /**
     * 联系人
     */
    private String linkedMan;

    /**
     * 电话
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * 地理坐标
     */
    private String axis;

    /**
     * 描述 简介
     */
    private String desc;

}
