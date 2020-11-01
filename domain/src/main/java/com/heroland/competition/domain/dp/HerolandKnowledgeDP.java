package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.DiffEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandKnowledgeDP extends BaseDO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

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


    public HerolandKnowledgeDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(course, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        // TODO: 2020/11/1  
//        AssertUtils.notBlank(grade, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(knowledge, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        DiffEnum diffEnum = DiffEnum.valueOfLevel(diff);
        // TODO: 2020/11/1  
//        AssertUtils.notNull(diffEnum, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        return this;
    }

    private List<HerolandKnowledgeDP> children = Lists.newArrayList();

}
