package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandInviteRecord;

public interface HeroLandInviteRecordExtMapper extends HeroLandInviteRecordMapper {

    int updateByRecordIdSelective(HeroLandInviteRecord record);

}