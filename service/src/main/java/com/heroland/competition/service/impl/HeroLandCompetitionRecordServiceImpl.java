package com.heroland.competition.service.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constant.HeroLandRedisConstants;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecordExample;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
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
    private RedisTemplate<String, HeroLandCompetitionRecordDP> redisTemplate;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        ResponseBody<String> result = new ResponseBody<>();
        String recordId;
        try {
            dp.addSynchronizeCheck();
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp);
            if (aBoolean == null || !aBoolean) {
                HeroLandCompetitionRecord heroLandCompetitionRecord = BeanUtil.insertConversion(dp, new HeroLandCompetitionRecord());
                recordId = heroLandCompetitionRecord.getRecordId();
                heroLandCompetitionRecordExtMapper.insert(heroLandCompetitionRecord);
                result.setData(recordId);
            } else {
                dp = redisTemplate.opsForValue().get(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey());
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
        redisTemplate.opsForValue().setIfAbsent(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp);
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
}
