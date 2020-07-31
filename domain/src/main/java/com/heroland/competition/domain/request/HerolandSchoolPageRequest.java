package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolPageRequest extends BaseQO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 详细的节点key
     */
    private String parentKey;

    /**
     * 批量key节点
     */
    private List<String> parentKeys;



}
