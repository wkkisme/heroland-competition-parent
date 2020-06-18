package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;

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
    ResponseBody<Boolean> addQuestion(HeroLandQuestionDP dp);

    /**
     * 题组删除
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteQuestion(HeroLandQuestionDP dp);

    /**
     * 题组增加题
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> addTopicQuestions(HeroLandQuestionDP dp);

    /**
     * 获取题组和题
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandQuestionDP>> getQuestionQuestions(HeroLandQuestionQO qo);

    /**
     * 获取题组和题
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandQuestionDP>> getQuestion(HeroLandQuestionQO qo);

}
