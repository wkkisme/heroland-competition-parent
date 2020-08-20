package com.heroland.competition.service;

import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.domain.dto.HeroLandTopicForSDto;
import com.heroland.competition.domain.qo.HerolandTopicGroupGradeQO;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
public interface HerolandTopicGroupPartService {

    Boolean addBatchDepartment(List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPs);

    Boolean deleteDepartment(List<Long> list);

    List<HeroLandTopicForSDto> listDepartmentByGrades(HerolandTopicGroupGradeQO qo);
}
