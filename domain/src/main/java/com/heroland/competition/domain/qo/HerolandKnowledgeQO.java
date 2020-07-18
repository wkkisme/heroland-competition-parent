package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class HerolandKnowledgeQO extends BaseQO implements Serializable {

    /**
     * 知识点
     * 模糊搜索
     */
    private String knowledge;

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 难度
     */
    private Integer diff;




}
