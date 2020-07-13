package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandCompetitionRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
public class HeroLandCompetitionRecordRemoteServiceImpl implements HeroLandCompetitionRecordRemoteService {
    @Override
    public ResponseBody<Boolean> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        return null;
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
        return null;
    }
}
