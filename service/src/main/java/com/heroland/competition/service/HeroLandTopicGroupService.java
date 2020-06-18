package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;

import java.util.List;

/**
 *  题组服务（赛事、比赛服务）
 * @author wangkai
 */
public interface HeroLandTopicGroupService {
    /**
     * 题组增加
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> addTopic(HeroLandTopicGroupDP dp);

    /**
     * 题组删除
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteTopic(HeroLandTopicGroupDP dp);


  /**
     * 获取题组和题
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandTopicGroupDP>> getTopic(HeroLandTopicGroupQO qo);



}
