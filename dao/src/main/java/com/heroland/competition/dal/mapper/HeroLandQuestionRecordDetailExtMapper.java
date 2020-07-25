package com.heroland.competition.dal.mapper;

import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandQuestionRecordDetailExtMapper extends HeroLandQuestionRecordDetailMapper {

    List<HeroLandQuestionRecordDetailDP> selectByTopicIdsAndUserId(@Param("topicIds") List<Long> topicIds,
                                                                   @Param("userId") String userId);

}