package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail;
import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandQuestionRecordDetailMapper {
    long countByExample(HeroLandQuestionRecordDetailExample example);

    int deleteByExample(HeroLandQuestionRecordDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandQuestionRecordDetail record);

    int insertSelective(HeroLandQuestionRecordDetail record);

    List<HeroLandQuestionRecordDetail> selectByExample(HeroLandQuestionRecordDetailExample example);

    HeroLandQuestionRecordDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandQuestionRecordDetail record, @Param("example") HeroLandQuestionRecordDetailExample example);

    int updateByExample(@Param("record") HeroLandQuestionRecordDetail record, @Param("example") HeroLandQuestionRecordDetailExample example);

    int updateByPrimaryKeySelective(HeroLandQuestionRecordDetail record);

    int updateByPrimaryKey(HeroLandQuestionRecordDetail record);

    int countCorrectAnswer(@Param("topicId") String topicId, @Param("questionId") Long questionId, @Param("userId") String excludeUserId);
}