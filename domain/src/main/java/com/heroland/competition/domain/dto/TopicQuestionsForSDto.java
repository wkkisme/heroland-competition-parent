package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @date 2020/8/20
 */
@Data
public class TopicQuestionsForSDto implements Serializable {

    private String userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 赛事id
     */
    private Long topicId;

    private String topicName;

    /**
     * 题目列表
     */
    private List<HeroLandQuestionBankSimpleDto>  questions = Lists.newArrayList();

    /**
     * 根据科目分类的题目
     * 默认是每一个科目12题，但是如果数据库中不够12题，则有多少返回多少
     */
    private Map<String, List<HeroLandQuestionBankSimpleDto>> questionsMap = Maps.newHashMap();

}
