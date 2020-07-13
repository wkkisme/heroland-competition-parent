package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.contants.DiffEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 */
@Data
public class HerolandChapterDP extends BaseDO implements Serializable {

    /**
     * 阶段
     */
    private String grade;

    private String gradeName;

    /**
     * 科目
     */
    private String course;
    private String courseName;

    /**
     * 版本
     */
    private String edition;
    private String editionName;

    /**
     * 章
     */
    private String chapter;
    /**
     * 单元
     */
    private String unit;

    /**
     * 节
     */
    private String section;

    /**
     * 章顺序
     */
    private Integer chapterOrder;

    /**
     * 单元顺序
     */
    private Integer unitOrder;

    /**
     * 节顺序
     */
    private Integer sectionOrder;

    /**
     * 难度 （章节的难度和知识点的难度单独定义）
     */
    private Integer diff;

    private String diffName;

    private String getDiffName(){
        DiffEnum diffEnum = DiffEnum.valueOfLevel(diff);
        if (Objects.nonNull(diffEnum)){
            this.diffName = diffEnum.getName();
            return this.diffName;
        }

        return null;
    }

}
