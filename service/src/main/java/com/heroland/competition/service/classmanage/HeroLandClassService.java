package com.heroland.competition.service.classmanage;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dp.HeroLandUserClassDP;
import com.heroland.competition.domain.dto.HeroLandUserClassInfoDto;
import com.heroland.competition.domain.dto.HeroLandUserDepartmentDto;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.domain.qo.HeroLandUserClassQO;
import com.heroland.competition.domain.request.UserClassRequest;
import com.heroland.competition.domain.request.UserDepartmentRequest;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;

import java.util.List;

/**
 * 班级管理
 *
 * @author mac
 */
public interface HeroLandClassService {


    ResponseBody<Boolean> addClass(HeroLandClassDP dp);

    ResponseBody<Boolean> updateClass(HeroLandClassDP recordDetail);

    ResponseBody<List<HeroLandUserClassDP>> getClassList(PlatformSysUserClassQO qo);

    ResponseBody<List<HeroLandUserClassInfoDto>> getClassInfoList(UserClassRequest request);


    ResponseBody<Boolean> deleteClassList(HeroLandClassManageQO qo);

    ResponseBody<Boolean> addClassUser(HeroLandUserClassDP dp);

    ResponseBody<Boolean> updateClassUser(HeroLandUserClassDP dp);

    ResponseBody<Boolean> deleteClassUser(HeroLandUserClassDP dp);

    ResponseBody<List<HeroLandUserClassDP>> getClassUser(HeroLandUserClassQO dp);

    ResponseBody<List<HeroLandUserDepartmentDto>> getClassUserDepartment(UserDepartmentRequest request);
}
