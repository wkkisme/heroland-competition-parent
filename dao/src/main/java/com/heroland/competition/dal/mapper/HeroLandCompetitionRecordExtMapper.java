package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandCompetitionRecordExtMapper extends HeroLandCompetitionRecordMapper {

    List<HeroLandCompetitionRecord> selectCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    Long countCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    List<HeroLandStatisticsTotal> getTotalScore(HeroLandStatisticsTotalQO qo);

    List<HeroLandStatisticsDetailDP> getTotalScoreDetail(HeroLandStatisticsTotalQO qo);

    List<HeroLandCompetitionRecord> selectByTopicIdsAndInviterId(@Param("topicIds") List<Long> topicIds,
                                                                 @Param("userId") String userId);
}