package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface HeroLandCompetitionRecordExtMapper extends HeroLandCompetitionRecordMapper {

    List<HeroLandCompetitionRecord> selectCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    Long countCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    List<HeroLandStatisticsTotal> getTotalScore(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailDP> getTotalScoreDetail(HeroLandStatisticsTotalQO qo);

    List<HeroLandStatisticsDetailDP> getAnswerRightRate(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailDP> getCompleteRate(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailDP> getWinRate(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailDP> getTotalTime(HeroLandStatisticsAllQO qo);

    List<HeroLandCompetitionRecord> selectByTopicIdsAndInviterId(@Param("topicIds") List<String> topicIds,
                                                                 @Param("userId") String userId);
}