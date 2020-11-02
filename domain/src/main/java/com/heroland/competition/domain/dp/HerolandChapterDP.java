package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.ChapterEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandChapterDP extends BaseDO implements Serializable {


    /**
     * 年级
     */
    private String grade;

    private Integer gradeUnit;

    /**
     * 科目
     */
    private String course;

    /**
     * 版本
     */
    private String edition;

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
    private List<Long> knowledges = Lists.newArrayList();


    public HerolandChapterDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(course, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(edition, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(grade, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(content, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notNull(contentType, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notNull(order, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        ChapterEnum chapterEnum = ChapterEnum.valueOfLevel(contentType);
        AssertUtils.notNull(chapterEnum, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        if (ChapterEnum.ZHANG.getType().equals(contentType)){
            parentId = 0L;
        }
        if (ChapterEnum.JIE.getType().equals(contentType) || ChapterEnum.KEJIE.getType().equals(contentType)){
            // TODO: 2020/11/1  
//            AssertUtils.assertThat(!NumberUtils.nullOrZeroLong(parentId), HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        return this;
    }

}
