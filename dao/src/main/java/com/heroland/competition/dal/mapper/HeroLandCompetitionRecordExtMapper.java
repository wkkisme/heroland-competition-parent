package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecordExample;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandCompetitionRecordExtMapper extends HeroLandCompetitionRecordMapper {

    List<HeroLandCompetitionRecord> selectCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    Long countCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);
}