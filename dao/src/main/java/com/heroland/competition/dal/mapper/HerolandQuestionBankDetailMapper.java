package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandQuestionBankDetail;

public interface HerolandQuestionBankDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandQuestionBankDetail record);

    int insertSelective(HerolandQuestionBankDetail record);

    HerolandQuestionBankDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandQuestionBankDetail record);

    int updateByPrimaryKey(HerolandQuestionBankDetail record);
}