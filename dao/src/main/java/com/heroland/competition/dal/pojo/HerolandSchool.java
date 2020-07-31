package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandSchool extends BaseDO {


    private String code;

    private String key;

    private Boolean hasParent;

    private String parentKey;

    private String name;

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

    /**
     * 默认值，例如班级的基本容量
     */
    private String defaultValue;


}