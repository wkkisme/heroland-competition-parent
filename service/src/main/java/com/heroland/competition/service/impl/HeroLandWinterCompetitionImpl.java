package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.factory.CompetitionTopicFactory;
import com.heroland.competition.service.HeroLandCompetitionService;
import org.apache.dubbo.config.annotation.Service;

/**
  寒假作业赛和应试赛一样
 * @author mac
 */
@Service
public class HeroLandWinterCompetitionImpl implements HeroLandCompetitionService {

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     * @return
     */
    @Override
    public Integer getType() {
        return CompetitionEnum.WINTER.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {
        return CompetitionTopicFactory.get(CompetitionEnum.EXAM.getType()).doAnswer(dp);
    }

}
