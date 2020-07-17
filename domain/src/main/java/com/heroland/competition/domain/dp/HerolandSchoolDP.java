package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolDP extends BaseDO implements Serializable {

    /**
     * 编号
     */
    private String code;

    /**
     * 本次需要生成的key
     */
    private String key;

    /**
     * 是否有父节点
     */
    private Boolean hasParent;

    /**
     * 父节点key
     */
    private String parentKey;

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


    public HerolandSchoolDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(code);
        AssertUtils.notBlank(parentKey);
        if (!StringUtils.isBlank(parentKey)){
            hasParent = Boolean.TRUE;
        }

        return this;
    }

}
