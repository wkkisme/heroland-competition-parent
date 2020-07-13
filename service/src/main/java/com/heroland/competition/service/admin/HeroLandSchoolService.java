package com.heroland.competition.service.admin;

import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.qo.HerolandSchoolQO;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */

public interface HeroLandSchoolService {



    Boolean addNode(HerolandSchoolDP schoolDP);

    Boolean deleteNode(String key);


    List<HerolandSchoolDto> queryChild(HerolandSchoolQO qo);


}
