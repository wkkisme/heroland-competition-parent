package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.MappingQuestions;
import com.heroland.competition.dal.pojo.MappingQuestionsExample;
import com.heroland.competition.dal.pojo.MappingQuestionsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MappingQuestionsMapper {
    long countByExample(MappingQuestionsExample example);

    int deleteByExample(MappingQuestionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MappingQuestionsWithBLOBs record);

    int insertSelective(MappingQuestionsWithBLOBs record);

    List<MappingQuestionsWithBLOBs> selectByExampleWithBLOBs(MappingQuestionsExample example);

    List<MappingQuestions> selectByExample(MappingQuestionsExample example);

    MappingQuestionsWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MappingQuestionsWithBLOBs record, @Param("example") MappingQuestionsExample example);

    int updateByExampleWithBLOBs(@Param("record") MappingQuestionsWithBLOBs record, @Param("example") MappingQuestionsExample example);

    int updateByExample(@Param("record") MappingQuestions record, @Param("example") MappingQuestionsExample example);

    int updateByPrimaryKeySelective(MappingQuestionsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MappingQuestionsWithBLOBs record);

    int updateByPrimaryKey(MappingQuestions record);
}