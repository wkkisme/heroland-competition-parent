package com.heroland.competition.service;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.request.HeroLandTopicAssignRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;

import java.util.List;

/**
 *  试卷题目服务
 * @author mac
 */
public interface HeroLandQuestionService{

    /**
     * 题增加
     * @param dp 对象
     * @return 正确
     */
    Boolean addQuestion(HeroLandQuestionDP dp);

    Boolean editTopic(HeroLandTopicGroupDP dp);

    /**
     * 题组删除
     * @param dp 对象
     * @return 正确
     */
    Boolean deleteTopic(Long id);

    /**
     * 题组增加题
     * @param dp 对象
     * @return 正确
     */
    Boolean addTopic(HeroLandTopicGroupDP dp);

    /**
     * 获取题组下的题
     * @param request
     * @return 值
     */
    PageResponse<HeroLandQuestionListForTopicDto> getTopicQuestions(HeroLandTopicQuestionsPageRequest request);

    Boolean saveAssign(HeroLandTopicAssignRequest request);

    HeroLandTopicDto getTopic(HeroLandTopicQuestionsPageRequest request);

    /**
     * 获取题组和题
     * @param qo 对象
     * @return 值
     */
//    List<HeroLandQuestionDP> getQuestion(HeroLandQuestionQO qo);

}
