package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandKnowledgeRefer;
import com.heroland.competition.dal.pojo.HerolandSchoolCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandKnowledgeReferMapper {
    int deleteByPrimaryKey(Long id);

//    int insert(HerolandKnowledgeRefer record);

    int insertSelective(HerolandKnowledgeRefer record);

    HerolandKnowledgeRefer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandKnowledgeRefer record);

//    int updateByPrimaryKey(HerolandKnowledgeRefer record);

    int batchSave(@Param("records") List<HerolandKnowledgeRefer> records);

    int deleteByReferId(@Param("referId") Long referId,@Param("type")  Integer type);

    int batchDeleteByReferIds(@Param("referIds") List<Long> referIds,@Param("type")  Integer type);

    List<HerolandKnowledgeRefer> selectByReferIds(@Param("referIds") List<Long> referIds,@Param("type")  Integer type);

    List<HerolandKnowledgeRefer> selectByKnowledgeIds(@Param("knowledgeIdIds") List<Long> knowledgeIdIds,@Param("type")  Integer type);
}