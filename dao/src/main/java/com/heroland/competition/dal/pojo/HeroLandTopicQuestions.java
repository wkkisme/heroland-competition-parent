package com.heroland.competition.dal.pojo;


import java.util.List;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

public class HeroLandTopicQuestions extends HeroLandTopicGroup {

    private List<HeroLandQuestion> questions;

    public List<HeroLandQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<HeroLandQuestion> questions) {
        this.questions = questions;
    }
}
