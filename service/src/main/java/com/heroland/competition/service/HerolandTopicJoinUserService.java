package com.heroland.competition.service;

import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.HerolandTopicCanJoinDto;
import com.heroland.competition.domain.dto.HerolandTopicCanSeeDto;
import com.heroland.competition.domain.dto.HerolandTopicJoinStatisticsDto;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
public interface HerolandTopicJoinUserService {

    Boolean addJoin(HerolandTopicJoinUserDP dp);

    Boolean cancel(HerolandTopicJoinUserDP dp);

    HerolandTopicJoinStatisticsDto statistics(HerolandTopicJoinUserDP dp);

    HerolandTopicCanSeeDto canOperableTopics(HerolandTopicCanSeeQO qo);
}
