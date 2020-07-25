package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class HerolandQuestionBankListForChapterRequest extends BaseQO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 题目类型
     *      OPTION(1,"选择题"),
     *
     *     FILL(2,"填空题"),
     *     ANSWER(3,"解答题"),
     *     COMPO(4,"综合题"),
     */
    private Integer type;

    /**
     * 题目的子类型，比如选择题的子类型有多选|单选
     * OPTION_ONE(11,"单选题"),
     * OPTION_MULTI(12,"多选题"),
     */
    private Integer subType;

    /**
     * 难度
     * ESAY(1,"容易"),
     *     HALF_ESAY(2,"较易"),
     *     MEDIUM(3,"中等"),
     *     HALF_DIFFICULT(4,"较难"),
     *     DIFFICULT(5,"难"),
     */
    private Integer diff;

    /**
     *
     *  ONE(1,"近1年"),
     *  THREE(2,"近3年"),
     *  FIVE(3,"近5年"),
     *  MORE(4,"更多"),
     *
     */
    private Integer yearRange;

    /**
     *
     *获取精确的那一年
     * 如果该字段有值则 yearRange 参数值忽略
     *
     */
    private String year;

    /**
     * 地区 如上海市
     */
    private String area;

    /**
     * 来源 如2016年上海市闵行区中考数学一模试卷
     */
    private String source;

    /**
     * 试题类型
     * MONI(32,"模拟考"),
     *     QIMO(34,"期末考"),
     *     QIZHONG(35,"期中考"),
     *     YUE(36,"月考"),
     *     ZHONG(31,"中考"),
     *     DANYUAN(38,"单元测试"),
     *     GAO(37,"高考"),
     *     OTHER(33,"其他"),
     */
    private Integer paperType;

    /**
     * 章节id
     */
    private List<Long> chapterIds;

}