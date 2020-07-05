package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandChapter;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 */
public interface HeroLandChapterMapper {

    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(HerolandChapter chapter);
    int insertSelective(HerolandChapter chapter);


    HerolandChapter selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(HerolandChapter record);

    List<HerolandChapter> selectByQuery(HerolandChapterQO qo);
}
