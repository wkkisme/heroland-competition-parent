package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Data
public class HerolandQuestionBank {

    private String gradeCode;

    private String course;

    private Integer type;

    private Integer subType;

    private Integer diff;

    private Date year;

    private String area;

    private String source;

    private Integer paperType;

    private String title;

    /**
     * 题目id
     * 由系统自己生成
     */
    private String qtId;

    /**
     * 快照号
     * 默认第一次新增时为1，后面每修改1次，快照号增加1
     */
    private Integer snapshotNo;

    private Long passageId;

    /**
     * 题库类型
     */
    private String storage;

    /**
     * 思维
     */
    private Integer think;

    /**
     * 题库类型
     * 为赛事而区分
     */
    private Integer bankType;

    /**
     * 相似题id
     * qtId
     */
    private List<String> optionZ = Lists.newArrayList();

    /**
     * 不知道是啥
     * 可暂时不管
     */
    private String passage;


    private Long id;
    private String creator;
    private Date gmtCreate;
    private Date gmtModified;
    private String modifier;
    private Boolean isDeleted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HerolandQuestionBank bank = (HerolandQuestionBank) o;
        return Objects.equals(id, bank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}