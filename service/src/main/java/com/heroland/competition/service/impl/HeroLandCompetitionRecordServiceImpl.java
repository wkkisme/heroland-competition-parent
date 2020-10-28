package com.heroland.competition.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.HeroLandRedisConstants;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionStatusEnum;
import com.heroland.competition.dal.mapper.HeroLandCompetitionRecordExtMapper;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupExtMapper;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupMapper;
import com.heroland.competition.dal.mapper.HerolandTopicQuestionExtMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.platfrom.payment.dto.PayOrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;
    @Resource
    private HerolandTopicQuestionExtMapper herolandTopicQuestionExtMapper;

    @Resource
    private HeroLandTopicGroupExtMapper heroLandTopicGroupExtMapper;

    @Resource
    private RedisService redisService;

    @Override
    public ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp) {
        ResponseBody<String> result = new ResponseBody<>();
        String recordId;
        try {
            dp.addCheck();
//            boolean aBoolean = redisService.setNx(HeroLandRedisConstants.COMPETITION + dp.getPrimaryRedisKey(), dp, "P1D");
//            if (!aBoolean) {
            HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupExtMapper.selectByPrimaryKey(Long.valueOf(dp.getTopicId()));
            if (ObjectUtil.isNull(heroLandTopicGroup)) {
                ResponseBodyWrapper.failException("题组不存在");
            }
            HeroLandCompetitionRecord heroLandCompetitionRecord = BeanUtil.insertConversion(dp, new HeroLandCompetitionRecord());
            recordId = heroLandCompetitionRecord.getRecordId();
            heroLandCompetitionRecord.setClassCode(heroLandTopicGroup.getClassCode());
            heroLandCompetitionRecord.setGradeCode(heroLandTopicGroup.getGradeCode());
            heroLandCompetitionRecord.setOrgCode(heroLandTopicGroup.getOrgCode());
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
    public ResponseBody<Boolean> addCompetitionAndDetail(HeroLandCompetitionRecordDP dp) {
        try {
            addCompetitionRecord(dp);
            return heroLandQuestionRecordDetailService.addQuestionRecords(dp.record2Detail());
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
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
    public ResponseBody<Boolean> updateCompetitionRecordByTopicId(HeroLandCompetitionRecordDP dp) {

        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            HeroLandCompetitionRecordExample heroLandCompetitionRecordExample = new HeroLandCompetitionRecordExample();
            heroLandCompetitionRecordExample.createCriteria().andTopicIdEqualTo(dp.getTopicId());
            result.setData(heroLandCompetitionRecordExtMapper.updateByExampleSelective(BeanUtil.updateConversion(dp.updateCheck(), new HeroLandCompetitionRecord()), heroLandCompetitionRecordExample) > 0);
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
        List<HeroLandCompetitionRecord> heroLandTopicGroups = new ArrayList<>();
        long count = 0L;
        try {

            heroLandTopicGroups = heroLandCompetitionRecordExtMapper.selectByRecordQO(qo);
            count = heroLandCompetitionRecordExtMapper.countByRecordQO(qo);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successListWrapper(heroLandTopicGroups, count, qo, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecordsAndDetail(HeroLandCompetitionRecordQO qo) {
        ResponseBody<List<HeroLandCompetitionRecordDP>> competitionRecords = getCompetitionRecords(qo);
        List<HeroLandCompetitionRecordDP> data = competitionRecords.getData();
        if (!CollectionUtils.isEmpty(data)) {
            Set<String> records = data.stream().map(HeroLandCompetitionRecordDP::getRecordId).collect(Collectors.toSet());
            HeroLandQuestionQO heroLandQuestionQO = new HeroLandQuestionQO();
            heroLandQuestionQO.setUserId(qo.getUserId());
            heroLandQuestionQO.setRecords(records);
            heroLandQuestionQO.setHistory(false);
            heroLandQuestionQO.setNeedPage(false);
            ResponseBody<List<HeroLandQuestionRecordDetailDP>> questionRecord = heroLandQuestionRecordDetailService.getQuestionRecord(heroLandQuestionQO);
            List<HeroLandQuestionRecordDetailDP> questionRecordData = questionRecord.getData();
            if (!CollectionUtils.isEmpty(questionRecordData)) {
                Map<String, List<HeroLandQuestionRecordDetailDP>> collect = questionRecordData.stream().collect(Collectors.groupingBy(HeroLandQuestionRecordDetailDP::getTopicId));
                data.forEach(v -> {
                    if (collect.get(v.getTopicId()) != null) {
                        v.setDetails(collect.get(v.getTopicId()));
                    }
                });
            }
        }

        return competitionRecords;

    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordByRecordId(HeroLandCompetitionRecordQO qo) {
        HeroLandCompetitionRecord heroLandCompetitionRecord = null;

        try {
            if (qo.getInviteRecordId() != null) {
                HeroLandCompetitionRecord record = (HeroLandCompetitionRecord) redisService.get("competition-record:" + qo.getInviteRecordId());
                if (record != null) {
                    heroLandCompetitionRecord = record;
                }
            }
            heroLandCompetitionRecord = heroLandCompetitionRecordExtMapper.selectByRecordId(qo.queryIdCheck().getRecordId());
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandCompetitionRecord, HeroLandCompetitionRecordDP.class);
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordByInviteRecordId(HeroLandCompetitionRecordQO recordId) {
        recordId.queryInviteIdCheck();
        try {
            HeroLandCompetitionRecord heroLandCompetitionRecord = heroLandCompetitionRecordExtMapper.selectByInviteRecordId(recordId.queryInviteIdCheck().getInviteRecordId());
            if (heroLandCompetitionRecord == null) {
                HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = (HeroLandCompetitionRecordDP) redisService.get("competition-record:" + recordId.getInviteRecordId());
                if (heroLandCompetitionRecordDP != null) {
                    return ResponseBodyWrapper.successWrapper(heroLandCompetitionRecordDP);
                }
            }
            if (heroLandCompetitionRecord == null) {
                Thread.sleep(500);
            }
            heroLandCompetitionRecord = heroLandCompetitionRecordExtMapper.selectByInviteRecordId(recordId.queryInviteIdCheck().getInviteRecordId());
            return ResponseBodyWrapper.successWrapper(heroLandCompetitionRecord, HeroLandCompetitionRecordDP.class);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.success();
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
            logger.info("计算完成率开始：{}", JSON.toJSONString(qo));
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
        List<HerolandTopicQuestion> total = herolandTopicQuestionExtMapper.countAll(qo);
             /*
                计算正确率和完成率
             */
        Map<String, List<String>> subject2Topic = qo.getSubject2Topic();
        Map<String, String> topic2Subject = qo.getTopic2Subject();
        Map<String, String> topic2OrgCode = qo.getTopic2OrgCode();
        if (!CollectionUtils.isEmpty(dps) && total != null ) {
            long count = total.stream().map(HerolandTopicQuestion::getTopicId).count();
            logger.info("dps:{}", JSON.toJSONString(dps));
            logger.info("totalCount:{}", JSON.toJSONString(total));
            total.forEach(v -> {
                v.setOrgCode(topic2OrgCode.get(v.getTopicId().toString()));
            });
            Map<String, List<HerolandTopicQuestion>> totalMap = total.stream().collect(Collectors.groupingBy(this::fetchKey));
            for (HeroLandStatisticsDetailDP v : dps) {
                if (v.getRightCount() == null) {
                    v.setRightCount(0L);
                }
                String topicId = v.getTopicId();
                String subject = topic2Subject.get(topicId);
                long totalCount = 0L;
                if (subject != null) {
                    // 拿出该subject下的所有topic
                    List<String> topics = subject2Topic.get(v.getOrgCode() + subject);
                    if (topics != null) {
                        if (CompetitionEnum.SYNC.getType().equals(qo.getType())) {
                            for (String tId : topics) {
                                List<HerolandTopicQuestion> questions = totalMap.get(tId);
                                if (questions != null) {
                                    totalCount = totalCount + questions.get(0).getTotalCount();
                                }
                            }
                            calRate(v, totalCount);
                        } else {
                            totalCount = topics.size();
                            calRate(v, totalCount);
                        }
                    }
                } else {

                    if (count > 0) {
                        totalCount = count;
                        calRate(v, totalCount);
                    } else {
                        v.setAnswerRightRate(0D);
                        v.setCompleteRate(0D);
                    }
                }

            }
        }
        return dps;
    }

    private String fetchKey(HerolandTopicQuestion question) {

        return question.getOrgCode() + question.getTopicId();
    }

    private void calRate(HeroLandStatisticsDetailDP v, Long totalCount) {
        if (totalCount != 0) {
            double rate = ((double) v.getRightCount() / (double) totalCount);
            if (rate >= 1) {
                v.setAnswerRightRate(1D);
                v.setCompleteRate(1D);
            } else {
                v.setAnswerRightRate(rate);
                v.setCompleteRate(rate);
            }
        } else {
            v.setAnswerRightRate(0D);
            v.setCompleteRate(0D);
        }
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
                        double v = (double) heroLandStatisticsDetailDp.getRightCount() / (double) landStatisticsDetailDp.getRightCount();
                        if (v > 1) {
                            landStatisticsDetailDp.setWinRate(1D);
                        } else {
                            landStatisticsDetailDp.setWinRate(v);
                        }
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

    @Override
    public HeroLandCompetitionRecordDP getRecordInfo(String topicId, String inviteId, String opponentId) {
        try {
            return BeanUtil.queryConversion(heroLandCompetitionRecordExtMapper.selectByTopicIdAndInviteIdAndOpponentId(topicId, inviteId, opponentId), new HeroLandCompetitionRecordDP());
        } catch (Exception e) {
            logger.error("e", e);
        }
        return null;
    }

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> getLatestCompetitionRecord(HeroLandCompetitionRecordQO qo) {
        qo.setPageSize(1);
        ResponseBody<HeroLandCompetitionRecordDP> result = new ResponseBody<>();
        ResponseBody<List<HeroLandCompetitionRecordDP>> competitionRecords = getCompetitionRecords(qo.latestCheck());
        List<HeroLandCompetitionRecordDP> data = competitionRecords.getData();
        if (!CollectionUtils.isEmpty(data)) {
            HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = data.get(0);
            if (CompetitionStatusEnum.COMPETING.getStatus().equals(heroLandCompetitionRecordDP.getStatus())) {
                result.setData(heroLandCompetitionRecordDP);
            }
        }
        return result;
    }
}
