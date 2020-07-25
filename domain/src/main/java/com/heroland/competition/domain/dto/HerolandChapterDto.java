package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandChapterDto extends BaseDO implements Serializable {


    /**
     * 年级
     */
    private String grade;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String course;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 版本
     */
    private String edition;

    /**
     * 版本名称
     */
    private String editionName;

    /**
     * 类型：
     * 1 章|单元 2 课节 3 小节
     */
    private Integer contentType;

    /**
     * 内容
     */
    private String content;

    /**
     * 父的节点id
     */
    private Long parentId;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 关联的知识点列表
     */
    private List<HerolandKnowledgeSimpleDto> knowledges = Lists.newArrayList();


}
