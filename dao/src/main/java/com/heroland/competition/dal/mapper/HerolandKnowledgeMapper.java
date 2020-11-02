package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 */
public interface HerolandKnowledgeMapper {

    int deleteByPrimaryKey(@Param("id") Long id);

    int insertSelective(HerolandKnowledge knowledge);

    int insertSelectiveWithId(HerolandKnowledge knowledge);

    int updateByPrimaryKeySelective(HerolandKnowledge knowledge);

    HerolandKnowledge selectByPrimaryKey(@Param("id") Long id);

    List<HerolandKnowledge> getByParentId(@Param("parentId") Long parentId);

    int updateByPrimaryKey(HerolandKnowledge record);

    List<HerolandKnowledge> selectByQuery(HerolandKnowledgeQO qo);

    List<HerolandKnowledge> selectByQuery2(HerolandKnowledgeQO qo);

    List<HerolandKnowledge> selectByIds(@Param("ids")List<Long> ids);

    List<HerolandKnowledge> selectBymMappingId(@Param("mappingId") String mappingId);

    List<HerolandKnowledge> selectBymMappingIdsAndGrade(@Param("mappingIds") List<String> mappingIds, @Param("grade") String grade);
}
