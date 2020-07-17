package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolRequest implements Serializable {


    /**
     * 详细的节点key
     * 如果为空会返回所有的地区信息
     * 查学校开始必须要有nodeKey
     * 传入某一个地区的key会返回他自己的信息，以及孩子节点学校列表信息，以此类推
     */
    private String nodeKey;
}
