package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.DiffEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
@Data
public class HerolandKnowledgeDP extends BaseDO implements Serializable {

    /**
     * 章节id
     */
    private Long chapterId;

    /**
     * 知识点
     */
    private String knowledge;

    /**
     * 是否是根级别
     */
    private Boolean isRoot;

    /**
     * 根级别知识点
     */
    private Long rootKnowledgeId;

    /**
     * 父级别知识点
     */
    private Long parentKnowledgeId;

    /**
     * 知识点难度level
     */
    private Integer diff;

    private String diffName;


    public String getDiffName(){
        DiffEnum diffEnum = DiffEnum.valueOfLevel(diff);
        if (Objects.nonNull(diffEnum)){
            this.diffName = diffEnum.getName();
            return this.diffName;
        }

        return null;
    }

    private List<HerolandKnowledgeDP> children = Lists.newArrayList();

}
