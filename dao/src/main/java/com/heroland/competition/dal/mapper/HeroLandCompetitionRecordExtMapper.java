package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailAll;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandCompetitionRecordExtMapper extends HeroLandCompetitionRecordMapper {

    List<HeroLandCompetitionRecord> selectCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    Long countCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO heroLandTopicGroupExample);

    List<HeroLandStatisticsTotal> getTotalScore(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailAll> getTotalScoreDetail(HeroLandStatisticsTotalQO qo);

    List<HeroLandStatisticsDetailAll> getDetailCount(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailAll> getCompleteRate(HeroLandStatisticsAllQO qo);

    List<HeroLandStatisticsDetailAll> getWinRate(HeroLandStatisticsTotalQO qo);

    List<HeroLandStatisticsDetailAll> getTotalTime(HeroLandStatisticsAllQO qo);

    List<HeroLandCompetitionRecord> selectByTopicIdsAndInviterId(@Param("topicIds") List<String> topicIds,
                                                                 @Param("userId") String userId);

    HeroLandCompetitionRecord selectByRecordId(String recordId);

    /**
     * @param recordId recordId
     * @return
     */
    HeroLandCompetitionRecord selectByInviteRecordId(String recordId);

    HeroLandCompetitionRecord selectByTopicIdAndInviteIdAndOpponentId(@Param("topicId") String topicId,
                                                                      @Param("inviteId") String inviteId,
                                                                      @Param("opponentId") String opponentId);

    List<HeroLandCompetitionRecord> selectByRecordQO(HeroLandCompetitionRecordQO qo);

    long countByRecordQO(HeroLandCompetitionRecordQO qo);
}