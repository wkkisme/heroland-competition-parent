package com.heroland.competition.service.admin;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;
import com.heroland.competition.domain.request.HerolandSchoolPageRequest;
import com.heroland.competition.domain.request.HerolandSchoolRequest;

import java.util.List;
import java.util.Map;

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

    Map<String, Integer> listCountByKeys(List<String> keys, String code);

    List<HerolandSchoolSimpleDto> getByKeys(List<String> keys, String code);

    /**
     * 根据子节点获取父节点信息
     * 如果keys为空，则是获取那一类，比如获取所有学校的地区信息
     * @param keys
     * @param code
     * @return
     */
    List<HerolandSchoolSimpleDto> getParentBySubNode(List<String> keys, String code);


}
