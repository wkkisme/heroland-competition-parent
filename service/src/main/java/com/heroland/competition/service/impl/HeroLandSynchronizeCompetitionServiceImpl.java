package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.service.HeroLandCalculatorService;
import com.heroland.competition.service.HeroLandCompetitionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mac
 */
@Service
public class HeroLandSynchronizeCompetitionServiceImpl implements HeroLandCompetitionService {

    @Resource
    private HeroLandCalculatorService heroLandCalculatorService;

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     * @return
     */
    @Override
    public Integer getType() {
        return 0;
    }

    /**
     * 介紹：做題的同時完成學校佈置功課；作業賽分為同步作業賽、寒假作業賽和暑期作業賽三種賽事。
     * 同步作業賽：根據教材的課節出題，有效地溫習所學內容。
     * 寒假作業賽：以上學期所學知識點相關題為競賽題目，在寒假期間競賽的同時溫故知新。
     * 暑期作業賽：以本年度所學知識點相關題為競賽題目，在暑期期間鞏固過去一年的學習。
     * 規則：答題限時2分鐘；任何一方答完即出結果，對或錯，分數。立即出勝負。
     * 勝負及計分方法：
     * 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。
     * 選擇不同英雄等級的對手，勝方計分方法：
     * 勝高兩級對手，得6分；
     * 勝高一級對手，得5分；
     * 勝同級別對手，得4分；
     * 勝低一級對手，得3分；
     * 勝低兩級對手，得2分。
     * 己方為和：己方答錯，對方亦答錯，得0分。
     * 己方為負：① 雙方都答對，己方遲答對，得1分。② 己方答錯，對方答對，得0分。
     * @param dp 答题
     * @return 结果
     */

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {


        return null;
    }
}
