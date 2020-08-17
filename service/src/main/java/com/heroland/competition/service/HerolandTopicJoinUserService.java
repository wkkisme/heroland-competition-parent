package com.heroland.competition.service;

import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.HerolandTopicJoinStatisticsDto;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
public interface HerolandTopicJoinUserService {

    Boolean join(HerolandTopicJoinUserDP dp);

    Boolean cancel(HerolandTopicJoinUserDP dp);

    HerolandTopicJoinStatisticsDto statistics(HerolandTopicJoinUserDP dp);
}
