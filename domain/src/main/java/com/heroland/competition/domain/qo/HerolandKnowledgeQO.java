package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
@Data
public class HerolandKnowledgeQO extends BaseQO implements Serializable {

    private Long id;

    /**
     * 知识点
     */
    private String knowledge;


}
