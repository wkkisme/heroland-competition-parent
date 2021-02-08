package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailExtMapper;
import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail;
import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetailExample;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wushuaiping
 * @date 2020/7/24 16:11
 */
@Service
public class HeroLandQuestionRecordDetailServiceImpl implements HeroLandQuestionRecordDetailService {

    private final Logger logger = LoggerFactory.getLogger(HeroLandQuestionRecordDetailServiceImpl.class);

    @Resource
    private HeroLandQuestionRecordDetailExtMapper questionRecordDetailExtMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    public ResponseBody<String> addQuestionRecord(HeroLandQuestionRecordDetailDP recordDetailDP) {
        logger.info("答题记录：{}", JSON.toJSONString(recordDetailDP));
        HeroLandQuestionRecordDetail heroLandQuestionRecordDetail = null;
        try {
            heroLandQuestionRecordDetail = BeanUtil.insertConversion(recordDetailDP.addCheck(), new HeroLandQuestionRecordDetail());
            questionRecordDetailExtMapper.insert(heroLandQuestionRecordDetail);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandQuestionRecordDetail.getRecordId());
    }

    @Override
    public ResponseBody<Boolean> addQuestionRecords(List<HeroLandQuestionRecordDetailDP> recordDetails) {

        for (HeroLandQuestionRecordDetailDP recordDetail : recordDetails) {
            recordDetail.addCheck();
        }
        try {
            List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = BeanUtil.queryListConversion(recordDetails, HeroLandQuestionRecordDetail.class);
            heroLandQuestionRecordDetails.forEach(v -> recordDetails.forEach(detail -> {
                if (v.getQuestionId().equals(detail.getQuestionId())) {
                    v.setCorrectAnswer(detail.getCorrectAnswer());
                    v.setAnswer(detail.getAnswer());
                    v.setType(detail.getTopicType());
                }
                HeroLandQuestionRecordDetailExample heroLandQuestionRecordDetailExample = new HeroLandQuestionRecordDetailExample();
                HeroLandQuestionRecordDetailExample.Criteria criteria = heroLandQuestionRecordDetailExample.createCriteria();
                criteria.andUserIdEqualTo(v.getUserId());
                criteria.andQuestionIdEqualTo(v.getQuestionId());
                criteria.andTopicIdEqualTo(v.getTopicId());
                v.setHistory(true);
                v.setId(null);
                questionRecordDetailExtMapper.updateByExampleSelective(v, heroLandQuestionRecordDetailExample);
                v.setHistory(false);
            }));

            questionRecordDetailExtMapper.insertBach(heroLandQuestionRecordDetails);
        } catch (Exception e) {
            logger.error("e", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<Boolean> updateQuestionRecord(HeroLandQuestionRecordDetailDP recordDetail) {

        try {

            questionRecordDetailExtMapper.updateByRecordIdSelective(BeanUtil.updateConversion(recordDetail.updateCheck(), new HeroLandQuestionRecordDetail()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandQuestionRecordDetailDP>> getQuestionRecord(HeroLandQuestionQO qo) {
        List<HeroLandQuestionRecordDetail> heroLandQuestions = Lists.newArrayList();
        long count = 0L;
        try {
            HeroLandQuestionRecordDetailExample example = new HeroLandQuestionRecordDetailExample();
            HeroLandQuestionRecordDetailExample.Criteria criteria = example.createCriteria();
            if (qo.getStartTime() != null) {
                criteria.andGmtCreateGreaterThanOrEqualTo(qo.getStartTime());
            }
            if (qo.getHistory() != null) {
                criteria.andHistoryEqualTo(qo.getHistory());
            }
            if (qo.getEndTime() != null) {
                criteria.andGmtCreateLessThanOrEqualTo(qo.getEndTime());
            }
            if (!StringUtils.isEmpty(qo.getTopicId())){
                criteria.andTopicIdIn(Lists.newArrayList(qo.getTopicId()));
            }
            if (!CollectionUtils.isEmpty(qo.getTopicIds())) {
                criteria.andTopicIdIn(qo.getTopicIds().stream().map(String::valueOf).collect(Collectors.toList()));
            }
            if (!CollectionUtils.isEmpty(qo.getRecords())) {
                criteria.andRecordIdIn(new ArrayList<>(qo.getRecords()));
            }
            if (!CollectionUtils.isEmpty(qo.getQuestionIds())) {
                criteria.andQuestionIdIn(new ArrayList<>(qo.getQuestionIds()));
            }
            if (!StringUtils.isEmpty(qo.getUserId())) {
                criteria.andUserIdEqualTo(qo.getUserId());
            }
            MybatisCriteriaConditionUtil.createExample(criteria, qo);
            if (qo.getNeedPage()) {
                example.setOrderByClause("gmt_create desc limit " + qo.getStartRow() + "," + qo.getPageSize());
            } else {
                example.setOrderByClause("gmt_create desc");
            }
            heroLandQuestions = questionRecordDetailExtMapper.selectByExample(example);
            count = questionRecordDetailExtMapper.countByExample(example);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        List<String> keys = Lists.newArrayList();
        keys.addAll(heroLandQuestions.stream().map(HeroLandQuestionRecordDetail::getSubjectCode).collect(Collectors.toList()));
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> data = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        heroLandQuestions.forEach(e -> {
            if (data.containsKey(e.getSubjectCode())){
                e.setSubjectName(data.get(e.getSubjectCode()).get(0).getDictValue());
            }
        });


        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandQuestionRecordDetailDP.class);
    }
}
