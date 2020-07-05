package com.heroland.competition.service.admin;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/5
 */
public interface HerolandKnowledgeService {

    /**
     * 增加知识点
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> add(HerolandKnowledgeDP dp);

    /**
     * 编辑知识点
     * 只改变知识点内容，不改变层级关系
     * 如果想改变层级关系可以通过删除再换节点添加
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> edit(HerolandKnowledgeDP dp);

    /**
     * 删除知识点
     * 删除某一知识点，则挂在下面的知识点内容也需要被删除，谨慎操作
     *
     * @param qo 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteAllByNode(HerolandKnowledgeQO qo);

    /**
     * 删除知识点
     * 删除某一知识点，则挂在下一级知识点内容向上挂，树节点向上偏移
     *
     * @param qo 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteOneNode(HerolandKnowledgeQO qo);

    /**
     * 获取某一个知识点
     * @param qo 对象
     */
    ResponseBody<HerolandKnowledgeDP> getById(HerolandKnowledgeQO qo);


    /**
     * 获取所有的知识点
     *
     * 分页获取
     */
    ResponseBody<List<HerolandKnowledgeDP>> pageQuery(HerolandKnowledgeQO qo);
}
