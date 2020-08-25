package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.service.HeroLandCompetitionService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 對賽規則，要麼12題對賽完結束，
 * 要麼連續戰勝對手4場結束，中間不能由和，
 * 要4題連勝就立刻終止對賽，並跳轉至勝負界面。
 * （注意：不管負方有沒有完成答題，也不管勝方做了多少題。）
 *
 * 每題最多2min，每題獨立計時，按選擇順序出題。
 * @author mac
 */
@Service
public class HeroLandSchoolCompetitionImpl implements HeroLandCompetitionService {

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     * @return
     */
    @Override
    public Integer getType() {
        return CompetitionEnum.SCHOOL.getType();
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {
        // 校级赛一题一题的答
        return null;
    }
}
