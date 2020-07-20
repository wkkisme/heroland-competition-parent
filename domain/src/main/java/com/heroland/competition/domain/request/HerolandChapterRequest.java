package com.heroland.competition.domain.request;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandChapterRequest implements Serializable {

    /**
     * 新增为空，编辑时必填
     */
    private Long id;

    /**
     * 年级 在编辑的时候不能修改
     */
    @NotNull
    private String grade;

    /**
     * 科目 在编辑的时候不能修改
     */
    @NotNull
    private String course;

    /**
     * 版本 在编辑的时候不能修改
     */
    @NotNull
    private String edition;

    /**
     * 类型：
     *1 章|单元 2 课节 3 小节
     *
     * 注意：在编辑的时候不能修改
     *
     */
    @NotNull
    private Integer contentType;

    /**
     * 内容
     */
    @NotNull
    private String content;

    /**
     * 顺序
     */
    @NotNull
    private Integer order;

    /**
     * 父的节点id 在编辑的时候不能修改
     */
    private Long parentId;

    /**
     * 关联的知识点列表
     */
    private List<Long> knowledges = Lists.newArrayList();
}
