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
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/24 16:11
 */
@Service
public class HeroLandQuestionRecordDetailServiceImpl implements HeroLandQuestionRecordDetailService {

    private final Logger logger = LoggerFactory.getLogger(HeroLandQuestionRecordDetailServiceImpl.class);

    @Resource
    private HeroLandQuestionRecordDetailExtMapper questionRecordDetailExtMapper;

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
            heroLandQuestionRecordDetails.forEach(v->{
                recordDetails.forEach(detail->{
                    if (v.getQuestionId().equalsIgnoreCase(detail.getQuestionId())){
                        v.setIsCorrectAnswer(detail.isCorrectAnswer());
                        v.setAnswer(detail.getAnswer());
                    }
                });
            });
            questionRecordDetailExtMapper.insertBach(heroLandQuestionRecordDetails);
        } catch (Exception e) {
            logger.error("e",e);
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
            MybatisCriteriaConditionUtil.createExample(criteria, qo);
            if(qo.getNeedPage()) {
                example.setOrderByClause("gmt_create desc limit " +qo.getStartRow() +","+qo.getPageSize() );
            }else {
                example.setOrderByClause("gmt_create desc");
            }
            heroLandQuestions = questionRecordDetailExtMapper.selectByExample(example);
            count = questionRecordDetailExtMapper.countByExample(example);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandQuestionRecordDetailDP.class);
    }
}
