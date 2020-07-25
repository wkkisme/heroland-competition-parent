package com.heroland.competition.service.admin;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dto.HerolandChapterDto;
import com.heroland.competition.domain.dto.HerolandChapterSimpleDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeSimpleDto;
import com.heroland.competition.domain.request.HerolandChapterKnowledgeRequest;
import com.heroland.competition.domain.request.HerolandChapterPageRequest;
import com.heroland.competition.domain.request.HerolandPreChapterRequest;

import java.util.List;

/**
 */
public interface HeroLandChapterService {

    /**
     * 增加章节内容
     * @param dp 对象
     * @return 正确
     */
    Boolean add(HerolandChapterDP dp);

    /**
     * 编辑章节内容
     * @param dp 对象
     * @return 正确
     */
    Boolean edit(HerolandChapterDP dp);

    /**
     * 删除章节内容
     *
     * @param id
     * @return 正确
     */
    Boolean delete(Long id);

    /**
     * 获取某一个章节条目
     * @param id
     */
    HerolandChapterDto getById(Long id);


    /**
     * 获取所有的章节
     *
     * 分页获取
     */
    PageResponse<HerolandChapterDto> pageQuery(HerolandChapterPageRequest request);


    List<HerolandKnowledgeSimpleDto> getChapterKnowledge(HerolandChapterKnowledgeRequest request);


    List<HerolandChapterSimpleDto> getChapterList(HerolandPreChapterRequest request);

}
