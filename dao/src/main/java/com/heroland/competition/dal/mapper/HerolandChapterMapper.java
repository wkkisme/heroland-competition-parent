package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandChapterMapper {
    int deleteByPrimaryKey(Long id);

//    int insert(HerolandChapter record);

    int insertSelective(HerolandChapter record);

    HerolandChapter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandChapter record);

    int updateByPrimaryKey(HerolandChapter record);

    List<HerolandChapter> getByParent(@Param("parentId") Long parentId);

    int batchDeleteByIds(@Param("ids") List<Long> ids);

    List<HerolandChapter> getByQuery(@Param("grade") String grade,@Param("course") String course,@Param("edition") String edition,@Param("contentType") Integer contentType,@Param("content") String content, @Param("parentId") Long parentId);

    List<HerolandChapter> getChapters(@Param("grade") String grade,@Param("gradeUnit") Integer gradeUnit,@Param("course") String course,@Param("edition") String edition,@Param("contentType") Integer contentType,@Param("content") String content, @Param("order") Integer order);

    List<HerolandChapter> getByIds(@Param("ids") List<Long> ids);

//    List<HerolandChapter> getByQuery( );
}