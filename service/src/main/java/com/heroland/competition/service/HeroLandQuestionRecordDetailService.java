package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/24 16:10
 */
public interface HeroLandQuestionRecordDetailService {

    ResponseBody<String> addQuestionRecord(HeroLandQuestionRecordDetailDP recordDetailDP);

    ResponseBody<Boolean> addQuestionRecords(List<HeroLandQuestionRecordDetailDP> recordDetails);

    ResponseBody<Boolean> updateQuestionRecord(HeroLandQuestionRecordDetailDP recordDetail);

    ResponseBody<List<HeroLandQuestionRecordDetailDP>> getQuestionRecord(HeroLandQuestionQO qo);
}
