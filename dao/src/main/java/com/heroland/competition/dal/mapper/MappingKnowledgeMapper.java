package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.MappingKnowledge;
import com.heroland.competition.dal.pojo.MappingKnowledgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MappingKnowledgeMapper {
    long countByExample(MappingKnowledgeExample example);

    int deleteByExample(MappingKnowledgeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MappingKnowledge record);

    int insertSelective(MappingKnowledge record);

    List<MappingKnowledge> selectByExample(MappingKnowledgeExample example);

    MappingKnowledge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MappingKnowledge record, @Param("example") MappingKnowledgeExample example);

    int updateByExample(@Param("record") MappingKnowledge record, @Param("example") MappingKnowledgeExample example);

    int updateByPrimaryKeySelective(MappingKnowledge record);

    int updateByPrimaryKey(MappingKnowledge record);
}