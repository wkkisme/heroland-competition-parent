package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandCompetitionRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
@DubboService(version = "1.0.0")
@AllArgsConstructor
public class HeroLandCompetitionRecordRemoteServiceImpl implements HeroLandCompetitionRecordRemoteService {

    private final HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        return heroLandCompetitionRecordService.addCompetitionRecord(dp);
    }

    @Override
    public ResponseBody<Boolean> updateCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> deleteCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO qo) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecords(HeroLandCompetitionRecordQO qo) {
        return null;
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordById(HeroLandCompetitionRecordQO recordId) {
        return heroLandCompetitionRecordService.getCompetitionRecordById(recordId);
    }
}
