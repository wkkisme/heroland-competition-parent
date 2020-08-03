package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.HeroLandRedisConstants;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.mapper.HerolandTopicQuestionExtMapper;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecord;
import com.heroland.competition.dal.pojo.HeroLandCompetitionRecordExample;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailAll;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private HerolandTopicQuestionExtMapper herolandTopicQuestionExtMapper;

    @Resource
    private RedisService redisService;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        ResponseBody<String> result = new ResponseBody<>();
        String recordId;
        try {
//            dp.addSynchronizeCheck();
//            boolean aBoolean = redisService.setNx(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp, "P1D");
//            if (!aBoolean) {
            HeroLandCompetitionRecord heroLandCompetitionRecord = BeanUtil.insertConversion(dp, new HeroLandCompetitionRecord());
            recordId = heroLandCompetitionRecord.getRecordId();
            heroLandCompetitionRecordExtMapper.insert(heroLandCompetitionRecord);
            result.setData(recordId);
            logger.info("返回比赛数据:{}", JSONObject.toJSONString(result));
//            } else {
//                dp = (HeroLandCompetitionRecordDP) redisService.get(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey());
//                result.setData(dp.getRecordId());
//            }
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
        redisService.setNx(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp, "P1D");
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
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordByRecordId(HeroLandCompetitionRecordQO qo) {
        HeroLandCompetitionRecord heroLandCompetitionRecord = null;

        try {
            heroLandCompetitionRecord = heroLandCompetitionRecordExtMapper.selectByRecordId(qo.queryIdCheck().getRecordId());
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandCompetitionRecord, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public List<HeroLandStatisticsDetailDP> getTotalScore(HeroLandStatisticsAllQO qo) {

        try {

            return BeanUtil.queryListConversion(heroLandCompetitionRecordExtMapper.getTotalScore(qo), HeroLandStatisticsDetailDP.class);
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }


    @Override
    public List<HeroLandStatisticsDetailDP> getAnswerRightRate(HeroLandStatisticsAllQO qo) {

        try {
            /*
                查出每个人答题正确数
             */
            qo.setIfCorrectAnswer(true);
            return getHeroLandStatisticsDetail(qo);
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }

    @Override
    public List<HeroLandStatisticsDetailDP> getCompleteRate(HeroLandStatisticsAllQO qo) {
        try {
            return getHeroLandStatisticsDetail(qo);
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }

    @NotNull
    private List<HeroLandStatisticsDetailDP> getHeroLandStatisticsDetail(HeroLandStatisticsAllQO qo) throws Exception {
        List<HeroLandStatisticsDetailDP> dps = BeanUtil.queryListConversion(heroLandCompetitionRecordExtMapper.getDetailCount(qo), HeroLandStatisticsDetailDP.class);
            /*
                查出所有比赛里有效题
             */
        Long totalCount = herolandTopicQuestionExtMapper.countAll();

             /*
                计算完成率
             */
        if (!CollectionUtils.isEmpty(dps) && totalCount > 0) {
            dps.forEach(v -> {
                if (v.getRightCount() == null) {
                    v.setRightCount(0L);
                }
                v.setAnswerRightRate((double) (v.getRightCount() / totalCount));
            });
        }
        return dps;
    }

    @Override
    public List<HeroLandStatisticsDetailDP> getWinRate(HeroLandStatisticsAllQO qo) {
        try {


            /*
             * 再查出总的
             */
            List<HeroLandStatisticsDetailAll> totalCount = heroLandCompetitionRecordExtMapper.getWinRate(qo);

            /*
             * 先查出正确的
             */
            qo.setResultInvite(0);
            qo.setResultOpponent(1);
            List<HeroLandStatisticsDetailAll> rightCount = heroLandCompetitionRecordExtMapper.getWinRate(qo);


            //  计算胜率，正确的/总答题数
            for (HeroLandStatisticsDetailAll heroLandStatisticsDetailDp : rightCount) {
                for (HeroLandStatisticsDetailAll landStatisticsDetailDp : totalCount) {

                    if (heroLandStatisticsDetailDp.getUserId().equals(landStatisticsDetailDp.getUserId())) {
                        landStatisticsDetailDp.setWinRate((double) (heroLandStatisticsDetailDp.getRightCount() / landStatisticsDetailDp.getRightCount()));
                    }
                }
            }

            // 没有的为零
            for (HeroLandStatisticsDetailAll landStatisticsDetailDp : totalCount) {
                if (landStatisticsDetailDp.getWinRate() == null) {
                    landStatisticsDetailDp.setWinRate(0.0);
                }
            }
            return BeanUtil.queryListConversion(totalCount, HeroLandStatisticsDetailDP.class);
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }

    @Override
    public List<HeroLandStatisticsDetailDP> getTotalTime(HeroLandStatisticsAllQO qo) {


        try {
            // 每个科目下的总时长

            return BeanUtil.queryListConversion(heroLandCompetitionRecordExtMapper.getTotalTime(qo), HeroLandStatisticsDetailDP.class);
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }
}
