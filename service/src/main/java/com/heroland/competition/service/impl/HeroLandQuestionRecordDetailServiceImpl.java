package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HeroLandQuestionRecordDetailMapper;
import com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wushuaiping
 * @date 2020/7/24 16:11
 */
@Service
public class HeroLandQuestionRecordDetailServiceImpl implements HeroLandQuestionRecordDetailService {

    private final Logger logger = LoggerFactory.getLogger(HeroLandQuestionRecordDetailServiceImpl.class);

    @Resource
    private HeroLandQuestionRecordDetailMapper heroLandQuestionRecordDetailMapper;

    @Override
    public ResponseBody<String> addQuestionRecord(HeroLandQuestionRecordDetailDP recordDetailDP) {
        logger.info("邀请记录：{}", JSON.toJSONString(recordDetailDP));
        HeroLandQuestionRecordDetail heroLandQuestionRecordDetail = null;
        try {
            heroLandQuestionRecordDetail = BeanUtil.insertConversion(recordDetailDP.addCheck(), new HeroLandQuestionRecordDetail());
            heroLandQuestionRecordDetailMapper.insert(heroLandQuestionRecordDetail);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandQuestionRecordDetail.getRecordId());
    }
}
