package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandQuestionBank;

public interface HerolandQuestionBankMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandQuestionBank record);

    int insertSelective(HerolandQuestionBank record);

    HerolandQuestionBank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandQuestionBank record);

    int updateByPrimaryKey(HerolandQuestionBank record);
}