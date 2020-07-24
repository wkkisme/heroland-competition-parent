package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.util.Date;


@Data
public class HerolandQuestionBank extends BaseDO {

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

}