package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.basic.HerolandChapter;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author smjyouzan
 * @date 2020/7/4
 */
public interface HeroLandChapterMapper {

    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(HerolandChapter chapter);


    HerolandChapter selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKey(HerolandChapter record);

    List<HerolandChapter> selectByQuery(HerolandChapterQO qo);
}
