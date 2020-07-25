package com.heroland.competition.service.admin;

import com.github.pagehelper.Page;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;
import com.heroland.competition.domain.request.HerolandSchoolPageRequest;
import com.heroland.competition.domain.request.HerolandSchoolRequest;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */

public interface HeroLandSchoolService {



    Boolean addNode(HerolandSchoolDP schoolDP);

    Boolean updateNode(HerolandSchoolDP schoolDP);

    Boolean deleteNode(String key);


    List<HerolandSchoolDto> queryChild(HerolandSchoolRequest request);


    PageResponse<HerolandSchoolSimpleDto> pageQuery(HerolandSchoolPageRequest request);


}