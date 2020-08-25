package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;

/**
 * 核心比赛
 * @author mac
 */
public interface HeroLandCompetitionService {

    /**
     * 标明自己的类型 {@link CompetitionEnum}
     * @return type
     */
    Integer getType();

    /**
     * 答题
     * @param dp 答题
     * @return 返回结果
     */
    ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp);
}
