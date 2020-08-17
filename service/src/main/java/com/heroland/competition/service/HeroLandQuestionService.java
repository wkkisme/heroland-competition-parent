package com.heroland.competition.service;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dp.QuestionTopicDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandQuestionTopicListDto;
import com.heroland.competition.domain.dto.HeroLandQuestionTopicListForStatisticDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.qo.HeroLandTopicQuestionsQo;
import com.heroland.competition.domain.request.HeroLandTopicAssignRequest;
import com.heroland.competition.domain.request.HeroLandTopicPageRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
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
    Long addTopic(HeroLandTopicGroupDP dp);

    /**
     * 获取题组下的题
     * @param request
     * @return 值
     */
    PageResponse<HeroLandQuestionListForTopicDto> getTopicQuestions(HeroLandTopicQuestionsPageRequest request);

    Boolean saveAssign(HeroLandTopicAssignRequest request);

    HeroLandTopicDto getTopic(HeroLandTopicPageRequest request);

    List<HeroLandQuestionTopicListDto> getTopicsQuestions(HeroLandTopicQuestionsQo qo);


    /**
     * 获取每一个赛事下的详细情况及题目数
     * @return 值
     */
    List<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForCourseStatistics(HeroLandTopicQuestionForCourseRequest request);

    /**
     * 获取每一个赛事下课节和知识点
     * @return 值
     */
    PageResponse<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForChapterStatistics(HeroLandTopicQuestionForCourseRequest request);

    PageResponse<QuestionTopicDP> getQuestionTopic(HeroLandTopicQuestionForCourseRequest request);

}
