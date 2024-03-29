package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandQuestionRecordDetailExtMapper extends HeroLandQuestionRecordDetailMapper {

    List<HeroLandQuestionRecordDetail> selectByTopicIdsAndUserId(@Param("topicIds") List<String> topicIds,
                                                                   @Param("userId") String userId);

    void updateByRecordIdSelective(HeroLandQuestionRecordDetail updateConversion);

    List<HeroLandQuestionRecordDetail> selectByCompetitionRecordId(@Param("recordIds")List<String> recordIds,
                                                                     @Param("userId") String userId);

    int insertBach(@Param("details")List<HeroLandQuestionRecordDetail> details);
}