package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandCompetitionRecordExtMapper {
    long countByExample(HeroLandCompetitionRecordExample example);

    int deleteByExample(HeroLandCompetitionRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandCompetitionRecord record);

    int insertSelective(HeroLandCompetitionRecord record);

    List<HeroLandCompetitionRecord> selectByExample(HeroLandCompetitionRecordExample example);

    HeroLandCompetitionRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandCompetitionRecord record, @Param("example") HeroLandCompetitionRecordExample example);

    int updateByExample(@Param("record") HeroLandCompetitionRecord record, @Param("example") HeroLandCompetitionRecordExample example);

    int updateByPrimaryKeySelective(HeroLandCompetitionRecord record);

    int updateByPrimaryKey(HeroLandCompetitionRecord record);
}