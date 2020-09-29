package com.heroland.competition.service.statistics;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.*;
import com.heroland.competition.domain.dto.AllCourseResultForWDto;
import com.heroland.competition.domain.dto.CourseResultForUserDto;
import com.heroland.competition.domain.dto.CourseResultForWDto;
import com.heroland.competition.domain.qo.*;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;

import java.util.List;

/**
 * 统计相关服务
 *
 * @author wangkai
 */
public interface HeroLandStatisticsWordService {

    /**
     *
     *
     * @param dp dp
     * @return Boolean
     */
    Boolean saveWorldStatistics(List<HeroLandStatisticsTotalDP> dp);

}
