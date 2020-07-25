package com.heroland.competition.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.HeroLandRedisConstants;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandCompetitionService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 比賽
 *
 * @author mac
 */
@Service
public class HeroLandCompetitionServiceImpl implements HeroLandCompetitionService {

    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandQuestionRecordDetailService questionRecordDetailService;

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     * 0 1 2 類型比賽都是一樣的，使用同一套積分規則就可以
     *
     * @return
     */
    @Override
    public Integer getType() {
        return 0;
    }

    /**
     * 答题
     *
     * @param dp 答题
     * @return 结果
     */
    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP dp) {

        // 获取比赛记录
        String recordId = dp.getRecordId();

        // 1 更新记录，插入详细
        HeroLandCompetitionRecordDP record = (HeroLandCompetitionRecordDP) redisService.get(HeroLandRedisConstants.COMPETITION + dp.getRecordId());

        // 先缓存查
        if (record == null) {
            record = heroLandCompetitionRecordService.getCompetitionRecordById(new HeroLandCompetitionRecordQO().setRecordId(recordId).queryIdCheck()).getData();
        }

        // 如果还是没有找到比赛 就报错
        if (record == null) {
            ResponseBodyWrapper.failException("该场比赛不存在");
        }

        // 每次答题生成一个答题记录
        List<HeroLandQuestionRecordDetailDP> details = dp.getDetails();
        if (CollUtil.isEmpty(details)) {
            ResponseBodyWrapper.failException("答题记录不能为空");
        }

        // 保存答题记录
        details.forEach(questionRecord -> {
            questionRecordDetailService.addQuestionRecord(questionRecord);
        });

        // 更新缓存
        redisService.set(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp);

        return ResponseBodyWrapper.successWrapper(dp);
    }
}
