package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/19
 */
@Data
public class HerolandChapterKnowledgeRequest implements Serializable {


    /**
     * 年级
     */
    @NotNull
    private String grade;

    /**
     * 科目
     */
    @NotNull
    private String course;

    /**
     * 章节
     * *1 章|单元 2 课节 3 小节
     */
    @NotNull
    private Integer chapterType;

    /**
     * 父节点id
     */
    private Long parentId;
}
