package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HerolandQuestionBankDP extends BaseDO implements Serializable {

    /**
     *
     */
    private Long passageId;

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

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 题目
     */
    private String title;

    /**
     * 题目类型
     */
    private Integer type;

    /**
     * 题目的子类型，比如选择题的子类型有多选|单选
     */
    private Integer subType;

    /**
     * 难度
     */
    private Integer diff;

    /**
     * 年份 如2006
     */
    private String year;

    private Date yearD;

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
     */
    private Integer paperType;

    /**
     * 选项内容
     */
    private List<QuestionOptionDto> options = Lists.newArrayList();

    /**
     * 选项答案|填空题答案
     */
    private String optionAnswer;

    /**
     * 胡乱答案
     */
    private String stormAnswer;

    /**
     * 答案解析
     */
    private String analysis;


    /**
     * 解答题答案
     */
    private String answer;

    /**
     * 解析（特殊场景）
     */
    private String parse;

    /**
     * 题库类型
     */
    private String storage;

    /**
     * 思维
     */
    private Integer think;

    private String information;

    /**
     * 题库类型
     * 为赛事而区分
     */
    private Integer bankType;




    /**
     * 关联知识点
     */
    private List<Long> knowledges = Lists.newArrayList();

    /**
     * 相似题，记录的是 qtId
     */
    private List<String> similarQt = Lists.newArrayList();


    public HerolandQuestionBankDP checkAndBuildBeforeCreate(){
        if (StringUtils.isEmpty(year)) {
            Date yearD = DateUtils.string2Date(year + "-01-01", "");
            this.yearD = yearD;
        }
        if (StringUtils.isEmpty(qtId)){
            this.qtId = IDGenerateUtils.getKey(AdminFieldEnum.QUEST);
        }
        if (NumberUtils.nullOrZero(snapshotNo)){
            this.snapshotNo = 1;
        }
        return this;
    }

    public HerolandQuestionBankDP appendOption(String option, String tag){
        QuestionOptionDto dto = new QuestionOptionDto();
        if (StringUtils.isEmpty(option)){
            return this;
        }
        dto.setOptionSerial(tag);
        dto.setContent(option);
        this.options.add(dto);
        return this;
    }

}