package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author smjyouzan
 * @date 2020/7/5
 */
public interface HerolandKnowledgeMapper {

    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(HerolandKnowledge chapter);

    HerolandKnowledge selectByPrimaryKey(@Param("id") Long id);

    List<HerolandKnowledge> getByParentId(@Param("parentId") Long parentId);

    int updateByPrimaryKey(HerolandKnowledge record);

    List<HerolandKnowledge> selectByQuery(HerolandKnowledgeQO qo);

}
