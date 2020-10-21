package com.heroland.competition.service;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.HeroLandTopicForSDto;
import com.heroland.competition.domain.dto.HerolandTopicCanJoinDto;
import com.heroland.competition.domain.dto.HerolandTopicCanSeeDto;
import com.heroland.competition.domain.dto.HerolandTopicJoinStatisticsDto;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;
import com.heroland.competition.domain.qo.HerolandTopicHeaderTeacherCanAssignQO;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
public interface HerolandTopicJoinUserService {

    Boolean addJoin(HerolandTopicJoinUserDP dp);

    Boolean cancel(HerolandTopicJoinUserDP dp);

    HerolandTopicJoinStatisticsDto statistics(HerolandTopicJoinUserDP dp);

    PageResponse<HeroLandTopicForSDto> canOperableTopics(HerolandTopicCanSeeQO qo);

    List<HeroLandTopicForSDto> getSchoolTopicsForHeaderTeacher(HerolandTopicHeaderTeacherCanAssignQO request);
}
