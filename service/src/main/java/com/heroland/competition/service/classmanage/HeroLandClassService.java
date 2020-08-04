package com.heroland.competition.service.classmanage;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dp.HeroLandUserClassDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.domain.qo.HeroLandUserClassQO;

import java.util.List;

/**
 * 班级管理
 *
 * @author mac
 */
public interface HeroLandClassService {


    ResponseBody<Boolean> addClass(HeroLandClassDP dp);

    ResponseBody<Boolean> updateClass(HeroLandClassDP recordDetail);

    ResponseBody<List<HeroLandClassDP>> getClassList(HeroLandClassManageQO qo);


    ResponseBody<Boolean> deleteClassList(HeroLandClassManageQO qo);

    ResponseBody<Boolean> addClassUser(HeroLandUserClassDP dp);

    ResponseBody<Boolean> updateClassUser(HeroLandUserClassDP dp);

    ResponseBody<Boolean> deleteClassUser(HeroLandUserClassDP dp);

    ResponseBody<List<HeroLandUserClassDP>> getClassUser(HeroLandUserClassQO dp);
}