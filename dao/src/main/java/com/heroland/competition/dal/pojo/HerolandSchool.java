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
}