package com.heroland.competition.service.classmanage;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;

import java.util.List;

/**
 * 班级管理
 * @author mac
 */
public interface HeroLandClassService {


    ResponseBody<Boolean> addClass(HeroLandClassDP dp);

    ResponseBody<Boolean> updateClass(HeroLandClassDP recordDetail);

    ResponseBody<List<HeroLandClassDP>> getClassList(HeroLandClassManageQO qo);


    ResponseBody<Boolean> deleteClassList(HeroLandClassManageQO qo);

}
