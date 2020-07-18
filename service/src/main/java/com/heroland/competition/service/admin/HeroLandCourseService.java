package com.heroland.competition.service.admin;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandCourseDP;
import com.heroland.competition.domain.dto.HerolandCourseDto;
import com.heroland.competition.domain.request.HerolandCoursePageRequest;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */

public interface HeroLandCourseService {



    Boolean addCourse(HerolandCourseDP courseDP);

    Boolean updateCourse(HerolandCourseDP schoolDP);

    Boolean deleteCourse(Long id);

    HerolandCourseDto getById(Long id);


    PageResponse<HerolandCourseDto> pageQuery(HerolandCoursePageRequest request);


}
