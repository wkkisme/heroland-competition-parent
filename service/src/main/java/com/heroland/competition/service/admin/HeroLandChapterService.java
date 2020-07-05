package com.heroland.competition.service.admin;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.qo.HerolandChapterQO;

import java.util.List;

/**
 */
public interface HeroLandChapterService {

    /**
     * 增加章节内容
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> add(HerolandChapterDP dp);

    /**
     * 编辑章节内容
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> edit(HerolandChapterDP dp);

    /**
     * 删除章节内容
     *
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> delete(HerolandChapterDP dp);

    /**
     * 获取某一个章节条目
     * @param qo 对象
     */
    ResponseBody<HerolandChapterDP> getById(HerolandChapterQO qo);


    /**
     * 获取所有的章节
     *
     * 分页获取
     */
    ResponseBody<List<HerolandChapterDP>> pageQuery(HerolandChapterQO qo);

}
