package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.MappingChapter;
import com.heroland.competition.dal.pojo.MappingChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MappingChapterMapper {
    long countByExample(MappingChapterExample example);

    int deleteByExample(MappingChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MappingChapter record);

    int insertSelective(MappingChapter record);

    List<MappingChapter> selectByExample(MappingChapterExample example);

    MappingChapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MappingChapter record, @Param("example") MappingChapterExample example);

    int updateByExample(@Param("record") MappingChapter record, @Param("example") MappingChapterExample example);

    int updateByPrimaryKeySelective(MappingChapter record);

    int updateByPrimaryKey(MappingChapter record);
}