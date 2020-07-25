package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.KnowledgeReferEnum;
import com.heroland.competition.common.contants.QtYearRangeEnum;
import com.heroland.competition.common.contants.TimeIntervalUnit;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandKnowledgeMapper;
import com.heroland.competition.dal.mapper.HerolandKnowledgeReferMapper;
import com.heroland.competition.dal.mapper.HerolandQuestionBankDetailMapper;
import com.heroland.competition.dal.mapper.HerolandQuestionBankMapper;
import com.heroland.competition.dal.pojo.HerolandKnowledgeRefer;
import com.heroland.competition.dal.pojo.HerolandQuestionBank;
import com.heroland.competition.dal.pojo.HerolandQuestionBankDetail;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HerolandQuestionBankQo;
import com.heroland.competition.domain.request.HerolandQuestionBankListForChapterRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
        bank.setPaperType(dp.getPaperType());
        bank.setTitle(dp.getTitle());
        bank.setYear(dp.getYearD());
        bank.setQtId(dp.getQtId());
        bank.setSnapshotNo(dp.getSnapshotNo());
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


    /**
     * 找到当前id的qtId
     * 将该id进行删除，重新生成1条数据 快照号加1
     * @param dp
     * @return
     */
    @Override
    @Transactional
    public Boolean editQuestion(HerolandQuestionBankDP dp) {
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByPrimaryKey(dp.getId());
        herolandQuestionBankMapper.deleteByPrimaryKey(dp.getId());
//        bank.getQtId()

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteByqtId(String qtId) {
        if (StringUtils.isEmpty(qtId)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        List<HerolandQuestionUniqDP> uniqDPS = herolandQuestionBankMapper.selectSimpleSnaphot(Lists.newArrayList(qtId));
        herolandQuestionBankMapper.deleteByQtIds(Lists.newArrayList(qtId));
        if (!CollectionUtils.isEmpty(uniqDPS)){
            List<Long> ids = uniqDPS.stream().map(HerolandQuestionUniqDP::getId).collect(Collectors.toList());
            herolandQuestionBankDetailMapper.deleteByQtId(ids);
        herolandKnowledgeReferMapper.batchDeleteByReferIds(ids,KnowledgeReferEnum.QUESTION.getType());
        }
//        //删除和知识点挂钩
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
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
    public HeroLandQuestionBankDto getById(String qtId) {
//        if (NumberUtils.nullOrZeroLong(id)){
//            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
//        }
//        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByPrimaryKey(id);
//        HeroLandQuestionBankDto bankDto = getAdminData(bank);
//        List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(Lists.newArrayList(id), KnowledgeReferEnum.QUESTION.getType());
//        List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
//        List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
//        if (!CollectionUtils.isEmpty(knowledgeIds)){
//            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
//            dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
//        }
//
//        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(Lists.newArrayList(id));
//        if (!CollectionUtils.isEmpty(byQtId)){
//            bankDto.setAnswer(byQtId.get(0).getAnswer());
//            bankDto.setOptionAnswer(byQtId.get(0).getOptionAnswer());
//            List<QuestionOptionDto> questionOptionDto = JSON.parseArray(byQtId.get(0).getOption(), QuestionOptionDto.class);
//            bankDto.setOptions(questionOptionDto);
//            bankDto.setParse(byQtId.get(0).getParse());
//        }
//        bankDto.setKnowledges(dtoList);

//        return bankDto;
        return null;
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
        HerolandQuestionBankQo qo = BeanCopyUtils.copyByJSON(request, HerolandQuestionBankQo.class);
        if (StringUtils.isEmpty(request.getYear())){
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
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

    @Override
    public PageResponse<HeroLandQuestionBankListForTopicDto> getQuestionList(HerolandQuestionBankListForChapterRequest request) {
        if (CollectionUtils.isEmpty(request.getChapterIds()) && StringUtils.isEmpty(request.getCourse()) && StringUtils.isEmpty(request.getGrade())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage()+", 请选择有效参数");
        }
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);
        if (!CollectionUtils.isEmpty(request.getChapterIds())){
            return questionListForChapter(request);
        }else {
            return questionListForCourse(request);
        }
    }

    private PageResponse<HeroLandQuestionBankListForTopicDto> questionListForChapter(HerolandQuestionBankListForChapterRequest request){
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);
        List<HerolandKnowledgeRefer> data = herolandKnowledgeReferMapper.selectByReferIds(request.getChapterIds(), KnowledgeReferEnum.CHAPTER.getType());

        Map<Long, List<HerolandKnowledgeRefer>> knowledgeMap = data.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getKnowledgeId));

        List<Long> knowledges = data.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(knowledges)){
            return pageResult;
        }
        HerolandQuestionBankQo qo = BeanCopyUtils.copyByJSON(request, HerolandQuestionBankQo.class);
        resolveYearRange(request.getYearRange(), qo);
        if (!StringUtils.isEmpty(request.getYear())){
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
        List<HerolandKnowledgeRefer> bankRefers = herolandKnowledgeReferMapper.selectByKnowledgeIds(knowledges, KnowledgeReferEnum.QUESTION.getType());
        if (CollectionUtils.isEmpty(bankRefers)){
            return pageResult;
        }
        List<Long> bankReferIds = bankRefers.stream().map(HerolandKnowledgeRefer::getReferId).collect(Collectors.toList());
        Map<Long, List<HerolandKnowledgeRefer>> bankIdKnowledgeMap = bankRefers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));

        qo.setBankIds(bankReferIds);
        Page<HerolandQuestionBank> banks = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.getByQuery(qo));

        pageResult= build(banks);
        if (!CollectionUtils.isEmpty(pageResult.getItems())){
            pageResult.getItems().stream().forEach(e -> {
                List<Long> chapterIds = Lists.newArrayList();
                if (bankIdKnowledgeMap.containsKey(e.getId())){
                    List<HerolandKnowledgeRefer> refers = bankIdKnowledgeMap.get(e.getId());
                    List<Long> ks = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
                    ks.stream().forEach(k -> {
                        if(knowledgeMap.containsKey(k)){
                            chapterIds.addAll(knowledgeMap.get(k).stream().map(HerolandKnowledgeRefer::getReferId).collect(Collectors.toList()));
                        }
                    });
                }
                e.setChapterId(chapterIds.stream().distinct().collect(Collectors.toList()));
            });
        }
        return pageResult;
    }

    private PageResponse<HeroLandQuestionBankListForTopicDto> questionListForCourse(HerolandQuestionBankListForChapterRequest request){
        HerolandQuestionBankQo qo = BeanCopyUtils.copyByJSON(request, HerolandQuestionBankQo.class);
        resolveYearRange(request.getYearRange(), qo);
        if (!StringUtils.isEmpty(request.getYear())){
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
        Page<HerolandQuestionBank> banks = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.getByQuery(qo));
       return build(banks);
    }

    private PageResponse<HeroLandQuestionBankListForTopicDto> build(Page<HerolandQuestionBank> banks){
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);

        if (CollectionUtils.isEmpty(banks.getResult())){
            return pageResult;
        }
        List<String> courseKeys = banks.getResult().stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.getResult().stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionBankListForTopicDto> chapterDtos  = Lists.newArrayList();
        List<Long> bankIds = banks.getResult().stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());

        Map<Long, List<HerolandQuestionBank>> bankIdMap = banks.getResult().stream().collect(Collectors.groupingBy(HerolandQuestionBank::getId));


        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));

        banks.getResult().stream().forEach(e -> {
            HeroLandQuestionBankListForTopicDto dto =   new HeroLandQuestionBankListForTopicDto();
            dto.setGrade(e.getGradeCode());
            dto.setCourse(e.getCourse());
            dto.setCourseName(keysMap.containsKey(e.getCourse()) ? keysMap.get(e.getCourse()).get(0).getDictValue() : "");
            dto.setGradeName(keysMap.containsKey(e.getGradeCode()) ? keysMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            dto.setArea(e.getArea());
            dto.setDiff(e.getDiff());
            dto.setPaperType(e.getPaperType());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setSubType(e.getSubType());
            dto.setSource(e.getSource());
            dto.setYear(StringUtils.isEmpty(e.getYear()) ? "": DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            if(qtMap.containsKey(e.getId())){
                dto.setAnswer(qtMap.get(e.getId()).get(0).getAnswer());
                dto.setOptionAnswer(qtMap.get(e.getId()).get(0).getOptionAnswer());
                List<QuestionOptionDto> questionOptionDto = JSON.parseArray(qtMap.get(e.getId()).get(0).getOption(), QuestionOptionDto.class);
                dto.setOptions(questionOptionDto);
            }
            chapterDtos.add(dto);

        });
        pageResult.setItems(chapterDtos);
        pageResult.setPageSize(banks.getPageSize());
        pageResult.setPage(banks.getPageNum());
        pageResult.setTotal((int) banks.getTotal());
        return pageResult;
    }

    private void resolveYearRange(Integer yearRange, HerolandQuestionBankQo qo){
        int num = 0;
        if (!NumberUtils.nullOrZero(yearRange)){
            QtYearRangeEnum qtYearRangeEnum = QtYearRangeEnum.valueOfLevel(yearRange);
            if (qtYearRangeEnum == null){
                num = -999;
            } else if (QtYearRangeEnum.ONE.getType().equals(yearRange)){
                num = 0;
            }else if (QtYearRangeEnum.THREE.getType().equals(yearRange)){
                num = -2;
            }
            else if (QtYearRangeEnum.FIVE.getType().equals(yearRange)){
                num = -4;
            }
            Date now = new Date();
            Date beginDate = DateUtils.plusDate(now, num, TimeIntervalUnit.YEAR);
            String nowYear = DateUtils.dateToYear(now);
            String beginYear = DateUtils.dateToYear(beginDate);

            Date beginTime = DateUtils.string2Date( beginYear+ "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(nowYear + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
    }

    private HeroLandQuestionBankDto getAdminData(HerolandQuestionBank bank){
        HeroLandQuestionBankDto bankDto = BeanCopyUtils.copyByJSON(bank, HeroLandQuestionBankDto.class);
        bankDto.setGrade(bank.getGradeCode());
        bankDto.setYear(StringUtils.isEmpty(bank.getYear()) ? "": DateUtils.dateToYear(bank.getYear()));
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


    public static void main(String[] args) {
        List<QuestionOptionDto> list = Lists.newArrayList();
        QuestionOptionDto questionOptionDto = new QuestionOptionDto();
        questionOptionDto.setContent("我是A");
        questionOptionDto.setOptionSerial("A");
        QuestionOptionDto questionOptionDto2 = new QuestionOptionDto();
        questionOptionDto2.setContent("我是B");
        questionOptionDto2.setOptionSerial("B");
        QuestionOptionDto questionOptionDto3 = new QuestionOptionDto();
        questionOptionDto3.setContent("我是C");
        questionOptionDto3.setOptionSerial("C");
        list.add(questionOptionDto);
        list.add(questionOptionDto2);
        list.add(questionOptionDto3);
        String string = JSON.toJSONString(list);
        System.out.println(string);
    }
}
