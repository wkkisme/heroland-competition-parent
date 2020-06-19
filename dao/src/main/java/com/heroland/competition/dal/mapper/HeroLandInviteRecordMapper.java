package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandInviteRecord;
import com.heroland.competition.dal.pojo.HeroLandInviteRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandInviteRecordMapper {
    long countByExample(HeroLandInviteRecordExample example);

    int deleteByExample(HeroLandInviteRecordExample example);

    int deleteByPrimaryKey(String recordId);

    int insert(HeroLandInviteRecord record);

    int insertSelective(HeroLandInviteRecord record);

    List<HeroLandInviteRecord> selectByExample(HeroLandInviteRecordExample example);

    HeroLandInviteRecord selectByPrimaryKey(String recordId);

    int updateByExampleSelective(@Param("record") HeroLandInviteRecord record, @Param("example") HeroLandInviteRecordExample example);

    int updateByExample(@Param("record") HeroLandInviteRecord record, @Param("example") HeroLandInviteRecordExample example);

    int updateByPrimaryKeySelective(HeroLandInviteRecord record);

    int updateByPrimaryKey(HeroLandInviteRecord record);
}