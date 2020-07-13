package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolDP extends BaseDO implements Serializable {

    private String code;

    private String key;

    private Boolean hasParent;

    private String parentKey;


    public HerolandSchoolDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(code);
        AssertUtils.notBlank(key);
        if (!StringUtils.isBlank(parentKey)){
            hasParent = Boolean.TRUE;
        }
        return this;
    }

}
