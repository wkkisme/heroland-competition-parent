package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2020/7/12
 * 对于一个题目来说后台有一个全局qtId，新增时版本号（snapshot）为1，主键id为A
 * 以后的每一次变化，都会将该qtId的版本号+1，主键id为B；并且老的版本失效（ID=A的delete），
 * 当分配题目时看到的是最新的版本号的主键id，如果某一段时间内后台对这个id对应的qtId进行了版本升级，
 * 还是可以看到分配时的版本，并且会告知该qtId下目前的最新版本是否和当时的一致，这里用的就是desc字段进行了描述
 * 如果不一致，并且qtId的题目没有被全部删除，newQuestionId代表最新版本的那个主键id
 */
@Data
public class HeroLandQuestionListForTopicDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目全局id
     */
    private String qtId;

    /**
     * 最新某一题目下的最新id
     */
    private Long newQuestionId;

    /**
     * 针对该全局id有两种情况
     * 题目更新了版本，当前的版本是上一次分配时的版本，可以保留或更换版本
     * 题目在后台已被删除，当前所有的版本都被删除了，不可以再选择
     * <p>
     * NORMAL:当初分配时的和现在的还是同一个版本
     * UPDATED：当初分配时的版本比现在的小，如果有需要可以更新到最新版本
     * DELETED：该题目在后台已经被删除
     */
    private String desc;

    /**
     * 年级
     */
    private String grade;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String course;

    /**
     * 科目名称
     */
    private String courseName;

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
     * 题库类型
     */
    private String storage;

    /**
     * 题库类型名称
     */
    private String storageName;

    /**
     * 思维
     */
    private Integer think;

    /**
     * 选项内容
     */
    private List<QuestionOptionDto> options;

    /**
     * 选项|填空题答案
     */
    private String optionAnswer;

    /**
     * 跨学科题答案解说
     */
    private String answer;

    /**
     * 胡乱答案
     */
    private String stormAnswer;

    /**
     * 答案解析
     */
    private String analysis;

    /**
     * 示例
     */
    private String information;

    private String similarQt;

    /**
     * 题组id
     */
    private Long topicId;

    /**
     * 关联的知识点列表
     */
    private List<HerolandKnowledgeSimpleDto> knowledges = Lists.newArrayList();

    private Integer result;

    private String opponent;

    /**
     * 是否回答正确
     */
    private Boolean correctAnswer;


    /**
     * 得分
     */
    private Integer score;


    private String topicName;

}
