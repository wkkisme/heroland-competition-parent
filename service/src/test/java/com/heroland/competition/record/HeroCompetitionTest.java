package com.heroland.competition.record;

import com.heroland.competition.base.BaseServiceTest;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class HeroCompetitionTest extends BaseServiceTest {
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;
//    @Test
    public void test(){
        List<HeroLandStatisticsDetailDP> answerRightRate = heroLandCompetitionRecordService.getAnswerRightRate(new HeroLandStatisticsAllQO());

    }
}
