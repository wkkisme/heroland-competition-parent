package com.heroland.competition.service.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constant.HeroLandRedisConstants;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecordExample;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛记录
 *
 * @author wangkai
 */
@Service
public class HeroLandCompetitionRecordServiceImpl implements HeroLandCompetitionRecordService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandCompetitionRecordServiceImpl.class);
    @Resource
    private HeroLandCompetitionRecordExtMapper heroLandCompetitionRecordExtMapper;

    @Resource
    private RedisService redisService;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        ResponseBody<String> result = new ResponseBody<>();
        String recordId;
        try {
            dp.addSynchronizeCheck();
            boolean aBoolean = redisService.setNx(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp,"24h");
            if (!aBoolean) {
                HeroLandCompetitionRecord heroLandCompetitionRecord = BeanUtil.insertConversion(dp, new HeroLandCompetitionRecord());
                recordId = heroLandCompetitionRecord.getRecordId();
                heroLandCompetitionRecordExtMapper.insert(heroLandCompetitionRecord);
                result.setData(recordId);
            } else {
                dp = (HeroLandCompetitionRecordDP) redisService.get(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey());
                result.setData(dp.getRecordId());
            }
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> updateCompetitionRecord(HeroLandCompetitionRecordDP dp) {

        ResponseBody<Boolean> result = new ResponseBody<>();
        // 更新缓存
        redisService.setNx(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp,"24h");
        try {
            result.setData(heroLandCompetitionRecordExtMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.updateCheck(), new HeroLandCompetitionRecord())) > 0);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<Boolean> deleteCompetitionRecord(HeroLandCompetitionRecordDP dp) {

        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            result.setData(heroLandCompetitionRecordExtMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.deleteCheck(), new HeroLandCompetitionRecord())) > 0);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO qo) {
        List<HeroLandCompetitionRecord> heroLandTopicGroups = new ArrayList<>();
        long count = 0L;
        try {

            heroLandTopicGroups = heroLandCompetitionRecordExtMapper.selectCompetitionRecordsAndQuestions(qo);
            count = heroLandCompetitionRecordExtMapper.countCompetitionRecordsAndQuestions(qo);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successListWrapper(heroLandTopicGroups, count, qo, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecords(HeroLandCompetitionRecordQO qo) {
        HeroLandCompetitionRecordExample heroLandTopicGroupExample = new HeroLandCompetitionRecordExample();
        MybatisCriteriaConditionUtil.createExample(heroLandTopicGroupExample.createCriteria(), qo);
        List<HeroLandCompetitionRecord> heroLandTopicGroups = new ArrayList<>();
        long count = 0L;
        try {

            heroLandTopicGroups = heroLandCompetitionRecordExtMapper.selectByExample(heroLandTopicGroupExample);
            count = heroLandCompetitionRecordExtMapper.countByExample(heroLandTopicGroupExample);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successListWrapper(heroLandTopicGroups, count, qo, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordById(HeroLandCompetitionRecordQO qo) {
        HeroLandCompetitionRecord heroLandCompetitionRecord = null;

        try {
            heroLandCompetitionRecord = heroLandCompetitionRecordExtMapper.selectByPrimaryKey(qo.queryIdCheck().getRecordId());
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandCompetitionRecord, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public List<HeroLandStatisticsTotalDP> getSyncTotalScore(HeroLandStatisticsTotalQO qo) {

        try {
            return BeanUtil.queryListConversion(heroLandCompetitionRecordExtMapper.getSyncTotalScore(qo),HeroLandStatisticsTotalDP.class);
        } catch (Exception e) {
            logger.error("e",e);
        }
        return null;
    }

    @Override
    public List<HeroLandStatisticsDetailDP> getSyncTotalScoreDetail(HeroLandStatisticsTotalQO qo) {

        try {
            return BeanUtil.queryListConversion(heroLandCompetitionRecordExtMapper.getSyncTotalScoreDetail(qo),HeroLandStatisticsDetailDP.class);
        } catch (Exception e) {
            logger.error("e",e);
        }
        return null;
    }
}
