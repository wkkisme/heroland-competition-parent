package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/20
 */
@Data
public class HerolandQuestionSelectQo extends BaseQO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 科目列表
     */
    private List<String> courseList;


    /**
     * 题目类型
     */
    private List<Integer> types;

    /**
     * 难度
     */
    private List<Integer> diffs;


    /**
     * 地区 如上海市
     */
    private List<String> areas;

    /**
     * 来源 如2016年上海市闵行区中考数学一模试卷
     */
    private List<String> sources;

    /**
     * 试题类型
     */
    private List<Integer> paperTypes;

    private Date beginTime;

    private Date endTime;

    private List<Long> bankIds;

    /**
     * 题库类型
     */
    private Integer bankType;

}
