package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/19
 */
@Data
public class HerolandChapterPageRequest extends BaseQO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 版本
     */
    private String edition;

    /**
     * 章节类型
     * 1 章|单元 2 课节 3 小节
     */
    private Integer contentType;

    /**
     * 内容
     * 支持模糊搜索
     * 如果有content， contentType无，则后台默认选择章
     */
    private String content;

    /**
     * 父节点id
     */
    private Long parentId;


}
