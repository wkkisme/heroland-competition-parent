package com.heroland.competition.remote;

import cn.hutool.core.util.ObjectUtil;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.api.HeroLandCompetitionRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
@Service(version = "1.0.0")
@AllArgsConstructor
public class HeroLandCompetitionRecordRemoteServiceImpl implements HeroLandCompetitionRecordRemoteService {

    private final HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        HeroLandCompetitionRecordDP recordDP = heroLandCompetitionRecordService.getRecordInfo(dp.getTopicId(), dp.getInviteId(), dp.getOpponentId());
        if (ObjectUtil.isNotNull(recordDP)){
            updateCompetitionRecord(dp);
            return ResponseBodyWrapper.successWrapper(recordDP.getRecordId());
        }else {
            return heroLandCompetitionRecordService.addCompetitionRecord(dp);
        }
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
        return heroLandCompetitionRecordService.getCompetitionRecordByRecordId(recordId);
    }
}
