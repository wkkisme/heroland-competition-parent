package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.KnowledgeReferEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandKnowledgeMapper;
import com.heroland.competition.dal.mapper.HerolandKnowledgeReferMapper;
import com.heroland.competition.dal.mapper.HerolandQuestionBankDetailMapper;
import com.heroland.competition.dal.mapper.HerolandQuestionBankMapper;
import com.heroland.competition.dal.pojo.HerolandChapter;
import com.heroland.competition.dal.pojo.HerolandKnowledgeRefer;
import com.heroland.competition.dal.pojo.HerolandQuestionBank;
import com.heroland.competition.dal.pojo.HerolandQuestionBankDetail;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HerolandQuestionBankQo;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
@Slf4j
@Component
public class HeroLandQuestionBankServiceImpl implements HeroLandQuestionBankService {

    @Resource
    private HerolandQuestionBankMapper herolandQuestionBankMapper;

    @Resource
    private HerolandQuestionBankDetailMapper herolandQuestionBankDetailMapper;

    @Resource
    private HerolandKnowledgeReferMapper herolandKnowledgeReferMapper;

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;


    @Override
    @Transactional
    public Boolean createQuestion(HerolandQuestionBankDP dp) {
        if (!NumberUtils.nullOrZeroLong(dp.getId())){
            return editQuestion(dp);
        }
        dp = dp.checkAndBuildBeforeCreate();
        HerolandQuestionBank bank = new HerolandQuestionBank();
        bank.setCourse(dp.getCourse());
        bank.setGradeCode(dp.getGrade());
        bank.setArea(dp.getArea());
        bank.setDiff(dp.getDiff());
        bank.setSource(dp.getSource());
        bank.setType(dp.getType());
        bank.setSubType(dp.getSubType());
        bank.setPaperTpye(dp.getPaperType());
        bank.setTitle(dp.getTitle());
        bank.setYear(dp.getYear());
        herolandQuestionBankMapper.insertSelective(bank);

        //添加detail相关部分
        HerolandQuestionBankDetail detail = new HerolandQuestionBankDetail();
        detail.setAnswer(dp.getAnswer());
        detail.setOption(JSON.toJSONString(dp.getOptions()));
        detail.setOptionAnswer(dp.getOptionAnswer());
        detail.setParse(dp.getParse());
        detail.setQbId(bank.getId());
        herolandQuestionBankDetailMapper.insertSelective(detail);
        if (!CollectionUtils.isEmpty(dp.getKnowledges())){
            List<HerolandKnowledgeRefer> list = Lists.newArrayList();
            dp.getKnowledges().stream().forEach(e -> {
                HerolandKnowledgeRefer refer = new HerolandKnowledgeRefer();
                refer.setKnowledgeId(e);
                refer.setReferId(bank.getId());
                refer.setType(KnowledgeReferEnum.QUESTION.getType());
                list.add(refer);
            });
            herolandKnowledgeReferMapper.batchSave(list);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean editQuestion(HerolandQuestionBankDP dp) {
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        herolandQuestionBankMapper.deleteByPrimaryKey(id);
        herolandQuestionBankDetailMapper.deleteByQtId(Lists.newArrayList(id));
        //删除和知识点挂钩
        herolandKnowledgeReferMapper.deleteByReferId(id,KnowledgeReferEnum.QUESTION.getType());
        return true;
    }

    @Override
    public HeroLandQuestionBankDto getById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByPrimaryKey(id);
        HeroLandQuestionBankDto bankDto = getAdminData(bank);
        List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(Lists.newArrayList(id), KnowledgeReferEnum.QUESTION.getType());
        List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
        List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(knowledgeIds)){
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
            dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
        }

        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(Lists.newArrayList(id));
        if (!CollectionUtils.isEmpty(byQtId)){
            bankDto.setAnswer(byQtId.get(0).getAnswer());
            bankDto.setOptionAnswer(byQtId.get(0).getOptionAnswer());
            List<QuestionOptionDto> questionOptionDto = JSON.parseArray(byQtId.get(0).getOption(), QuestionOptionDto.class);
            bankDto.setOptions(questionOptionDto);
            bankDto.setParse(byQtId.get(0).getParse());
        }
        bankDto.setKnowledges(dtoList);

        return bankDto;
    }

    private HerolandKnowledgeSimpleDto convert(HerolandKnowledge knowledge){
        HerolandKnowledgeSimpleDto dto = new HerolandKnowledgeSimpleDto();
        dto.setKnowledge(knowledge.getKnowledge());
        dto.setId(knowledge.getId());
        return dto;
    }

    @Override
    public PageResponse<HeroLandQuestionBankSimpleDto> pageQuery(HerolandQuestionBankPageRequest request) {
        List<HeroLandQuestionBankSimpleDto> result = new ArrayList<>();
        PageResponse<HeroLandQuestionBankSimpleDto> pageResult = new PageResponse<>();
        HerolandQuestionBankQo qo = new HerolandQuestionBankQo();
        Page<HerolandQuestionBank> data = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.getByQuery(qo));
        if (!CollectionUtils.isEmpty(data.getResult())){
            List<Long> qbIds = data.getResult().stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());

            List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(qbIds, KnowledgeReferEnum.QUESTION.getType());

            List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
            Map<Long, List<HerolandKnowledgeRefer>> qbMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));

            List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(knowledgeIds)){
                List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
                dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
            }

            Map<Long, List<HerolandKnowledgeSimpleDto>> knowledgeMap = dtoList.stream().collect(Collectors.groupingBy(HerolandKnowledgeSimpleDto::getId));
            data.getResult().stream().forEach(e -> {
                List<HerolandKnowledgeSimpleDto> simpleDtos = Lists.newArrayList();
                HeroLandQuestionBankDto bankDto = getAdminData(e);
                HeroLandQuestionBankSimpleDto simpleDto = BeanCopyUtils.copyByJSON(bankDto, HeroLandQuestionBankSimpleDto.class);
                List<HerolandKnowledgeRefer> qbKnowledge = qbMap.get(e.getId());
                if (!CollectionUtils.isEmpty(qbKnowledge)){
                    List<Long> chapterKnowledgeIds =  qbKnowledge.stream().map(HerolandKnowledgeRefer::getKnowledgeId).collect(Collectors.toList());
                    chapterKnowledgeIds.stream().forEach(id -> simpleDtos.addAll(knowledgeMap.get(id)));
                }
                bankDto.setKnowledges(simpleDtos);
                result.add(simpleDto);
            });
        }
        pageResult.setItems(result);
        pageResult.setPageSize(data.getPageSize());
        pageResult.setPage(data.getPageNum());
        pageResult.setTotal((int) data.getTotal());
        return pageResult;
    }

    private HeroLandQuestionBankDto getAdminData(HerolandQuestionBank bank){
        HeroLandQuestionBankDto bankDto = BeanCopyUtils.copyByJSON(bank, HeroLandQuestionBankDto.class);
        bankDto.setGrade(bank.getGradeCode());
        List<String> keys = Lists.newArrayList();
        keys.add(bank.getCourse());
        keys.add(bank.getGradeCode());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(bank.getCourse())){
            bankDto.setCourseName(keyMap.get(bank.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(bankDto.getGrade())){
            bankDto.setGradeName(keyMap.get(bankDto.getGrade()).get(0).getDictValue());
        }
        return bankDto;
    }
}
