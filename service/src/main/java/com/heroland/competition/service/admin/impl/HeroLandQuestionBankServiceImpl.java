package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.poi.word.Question;
import com.anycommon.poi.word.WordFileService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.*;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.*;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.HerolandKnowledgeRefer;
import com.heroland.competition.dal.pojo.HerolandQuestionBank;
import com.heroland.competition.dal.pojo.HerolandQuestionBankDetail;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankImportDP;
import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.domain.qo.HerolandQuestionBankQo;
import com.heroland.competition.domain.qo.HerolandQuestionSelectQo;
import com.heroland.competition.domain.request.HerolandQuestionBankListForChapterRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.docx4j.wml.Numbering;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
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

    @Resource
    private WordFileService wordFileService;

    @Resource
    private HerolandBasicDataMapper herolandBasicDataMapper;


    @Override
    public Boolean createQuestion(HerolandQuestionBankDP dp) {
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
        bank.setStorage(dp.getStorage());
        bank.setThink(dp.getThink());
        bank.setPassageId(dp.getPassageId());
        bank.setBankType(dp.getBankType());
        herolandQuestionBankMapper.insertSelective(bank);

        //添加detail相关部分
        HerolandQuestionBankDetail detail = new HerolandQuestionBankDetail();
        detail.setAnswer(dp.getAnswer());
        detail.setOption(JSON.toJSONString(dp.getOptions()));
        detail.setOptionAnswer(dp.getOptionAnswer());
        detail.setParse(dp.getParse());
        detail.setQbId(bank.getId());
        detail.setAnalysis(dp.getAnalysis());
        detail.setStormAnswer(dp.getStormAnswer());
        detail.setInformation(dp.getInformation());
        detail.setSimilarQt(JSON.toJSONString(dp.getSimilarQt()));
        herolandQuestionBankDetailMapper.insertSelective(detail);
        if (!CollectionUtils.isEmpty(dp.getKnowledges())) {
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
    public Boolean importQuestion(List<HerolandQuestionBankImportDP> importDPS) {
        if (CollectionUtils.isEmpty(importDPS)) {
            return true;
        }
        List<HerolandQuestionBankDP> bankDPS = Lists.newArrayList();
        importDPS.stream().forEach(e -> {
            HerolandQuestionBankDP bankDP = new HerolandQuestionBankDP();
            bankDP.setAnalysis(e.getAnswer2());
            bankDP.setCourse(e.getSubjectid());
            bankDP.setDiff(e.getDiff());
            bankDP.setGrade(e.getGradeid());
            bankDP.setOptionAnswer(e.getAnswer1());
            bankDP.setParse(e.getParse());
            bankDP.setPassageId(e.getPassageid());
            bankDP.setThink(e.getThink());
            bankDP.setTitle(e.getQuestion());
            bankDP.setType(e.getQtype());
            bankDP.setStormAnswer(e.getAnswer0());
            bankDP.setStorage(e.getStorage());
            bankDP.setInformation(e.getInformation());
            bankDP.setBankType(e.getBankType());
            try {
//                if (!StringUtils.isEmpty(e.getKnowledgeId())) {
//                    List<Long> knowledges = Arrays.asList(e.getKnowledgeId().split(",")).stream().map(NumberUtils::parseLong).distinct().collect(Collectors.toList());
//                    bankDP.setKnowledges(knowledges);
//                }
                if (!CollectionUtils.isEmpty(e.getKnowledgeIds())) {
                    bankDP.setKnowledges(e.getKnowledgeIds());
                }

            } catch (Exception ex) {
                ResponseBodyWrapper.failException("知识点id" + HerolandErrMsgEnum.ERROR_PARSE.getErrorMessage());
            }
            try {
                if (!StringUtils.isEmpty(e.getOption_z())) {
                    List<String> similar = Arrays.asList(e.getOption_z().split(",")).stream().distinct().collect(Collectors.toList());
                    bankDP.setSimilarQt(similar);
                }
            } catch (Exception ex) {
                ResponseBodyWrapper.failException("相似题目" + HerolandErrMsgEnum.ERROR_PARSE.getErrorMessage());
            }
            bankDP.appendOption(e.getOption_a(), "A")
                    .appendOption(e.getOption_b(), "B")
                    .appendOption(e.getOption_c(), "C")
                    .appendOption(e.getOption_d(), "D")
                    .appendOption(e.getOption_e(), "E");
            bankDPS.add(bankDP);

        });

        List<List<HerolandQuestionBankDP>> split = BatchUtils.split(bankDPS, 20);
        split.stream().forEach(list -> {
            batchSave(list);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                log.error("sleep error");
            }
        });

        return true;
    }

    private void batchSave(List<HerolandQuestionBankDP> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(e -> createQuestion(e));
    }


    /**
     * 找到当前id的qtId
     * 将该id进行删除，重新生成1条数据 快照号加1
     *
     * @param dp
     * @return
     */
    @Override
    public Boolean editQuestion(HerolandQuestionBankDP dp) {
        if (StringUtils.isEmpty(dp.getQtId())) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByQtId(dp.getQtId());
        if (bank != null){
            herolandQuestionBankMapper.deleteByPrimaryKey(dp.getId());
        }
        dp.setSnapshotNo(bank.getSnapshotNo() + 1);
        dp.setQtId(dp.getQtId());
        dp.setId(null);
        createQuestion(dp);
        return true;
    }

    @Override
    public Boolean deleteByqtId(String ids) {
//        if (StringUtils.isEmpty(ids)) {
//            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
//        }
//        List<Long> idList = Arrays.asList(ids.split(",")).stream().map(NumberUtils::parseLong).distinct().collect(Collectors.toList());
//
//
//        bankDP.setKnowledges(knowledges);
//
//
//        List<HerolandQuestionUniqDP> uniqDPS = herolandQuestionBankMapper.selectSimpleSnaphot(Lists.newArrayList(qtId));
//        herolandQuestionBankMapper.deleteByQtIds(Lists.newArrayList(qtId));
//        if (!CollectionUtils.isEmpty(uniqDPS)) {
//            List<Long> ids = uniqDPS.stream().map(HerolandQuestionUniqDP::getId).collect(Collectors.toList());
//            herolandQuestionBankDetailMapper.deleteByQtId(ids);
//            herolandKnowledgeReferMapper.batchDeleteByReferIds(ids, KnowledgeReferEnum.QUESTION.getType());
//        }
//        //删除和知识点挂钩
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        herolandQuestionBankMapper.deleteByPrimaryKey(id);
        herolandQuestionBankDetailMapper.deleteByQtId(Lists.newArrayList(id));
        //删除和知识点挂钩
        herolandKnowledgeReferMapper.deleteByReferId(id, KnowledgeReferEnum.QUESTION.getType());
        return true;
    }

    @Override
    public HeroLandQuestionBankDto getById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByPrimaryKey(id);
        HeroLandQuestionBankDto bankDto = build(bank);
        return bankDto;
    }

    private HeroLandQuestionBankDto build(HerolandQuestionBank bank){
        if (bank == null){
            return null;
        }
        HeroLandQuestionBankDto bankDto = getAdminData(bank);
        List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(Lists.newArrayList(bank.getId()), KnowledgeReferEnum.QUESTION.getType());
        List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
        List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(knowledgeIds)) {
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
            dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
        }

        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(Lists.newArrayList(bank.getId()));
        if (!CollectionUtils.isEmpty(byQtId)) {
            bankDto.setAnswer(byQtId.get(0).getAnswer());
            bankDto.setOptionAnswer(byQtId.get(0).getOptionAnswer());
            List<QuestionOptionDto> questionOptionDto = JSON.parseArray(byQtId.get(0).getOption(), QuestionOptionDto.class);
            bankDto.setOptions(questionOptionDto);
            bankDto.setParse(byQtId.get(0).getParse());
            bankDto.setAnalysis(byQtId.get(0).getAnalysis());
            bankDto.setInformation(byQtId.get(0).getInformation());
        }
        bankDto.setKnowledges(dtoList);
        return bankDto;
    }

    @Override
    public HeroLandQuestionBankDto getByQtId(String qtId) {
        if (StringUtils.isEmpty(qtId)) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        HerolandQuestionBank bank = herolandQuestionBankMapper.selectByQtId(qtId);
        HeroLandQuestionBankDto bankDto = build(bank);
        return bankDto;

    }

    private HerolandKnowledgeSimpleDto convert(HerolandKnowledge knowledge) {
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
        if (!StringUtils.isEmpty(request.getYear())) {
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
//        Page<HerolandQuestionBank> data = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
//                () -> herolandQuestionBankMapper.getByQuery(qo));

        Page<HerolandQuestionBank> data = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.getByQueryV2(qo));
        if (!CollectionUtils.isEmpty(data.getResult())) {
            List<Long> qbIds = data.getResult().stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());

            List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(qbIds, KnowledgeReferEnum.QUESTION.getType());

            List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
            Map<Long, List<HerolandKnowledgeRefer>> qbMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));

            List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(knowledgeIds)) {
                List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
                dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
            }

            Map<Long, List<HerolandKnowledgeSimpleDto>> knowledgeMap = dtoList.stream().collect(Collectors.groupingBy(HerolandKnowledgeSimpleDto::getId));
            data.getResult().stream().forEach(e -> {
                List<HerolandKnowledgeSimpleDto> simpleDtos = Lists.newArrayList();
                HeroLandQuestionBankDto bankDto = getAdminData(e);
                HeroLandQuestionBankSimpleDto simpleDto = BeanCopyUtils.copyByJSON(bankDto, HeroLandQuestionBankSimpleDto.class);
                List<HerolandKnowledgeRefer> qbKnowledge = qbMap.get(e.getId());
                if (!CollectionUtils.isEmpty(qbKnowledge)) {
                    List<Long> chapterKnowledgeIds = qbKnowledge.stream().map(HerolandKnowledgeRefer::getKnowledgeId).collect(Collectors.toList());
                    chapterKnowledgeIds.stream().forEach(id -> simpleDtos.addAll(CollectionUtils.isEmpty(knowledgeMap.get(id)) ? Lists.newArrayList() : knowledgeMap.get(id)));
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
        if (CollectionUtils.isEmpty(request.getChapterIds()) && StringUtils.isEmpty(request.getCourse()) && StringUtils.isEmpty(request.getGrade())) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage() + ", 请选择有效参数");
        }
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);
        if (!CollectionUtils.isEmpty(request.getChapterIds())) {
            return questionListForChapter(request);
        } else {
            return questionListForCourse(request);
        }
    }

    @Override
    public ResponseBody<Boolean> importQuestions(MultipartHttpServletRequest request,Integer bankType) throws Exception {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            if (entry.getValue() instanceof CommonsMultipartFile) {
                CommonsMultipartFile file = (CommonsMultipartFile) entry.getValue();
                FileItem fileItem = file.getFileItem();
                String property = System.getProperty("user.dir");


                String s = property + "/portal/src/main/resources/static/html/" + UUID.randomUUID() + ".html";
                File html = new File(s);
                //文件不存在则创建文件，先创建目录
                if (!html.exists()) {
                    File dir = new File(html.getParent());
                    boolean mkdirs = dir.mkdirs();
                    boolean newFile = html.createNewFile();
                }

                FileOutputStream outStream = new FileOutputStream(html);    //文件输出流用于将数据写入文件
                outStream.write("".getBytes());
                outStream.close();    //关闭文件输出流

                try {
                    List<Question> questions = wordFileService.importWord(Lists.newArrayListWithCapacity(50), Question.class, fileItem.getInputStream(), s, property + "/portal/src/main/resources/static/word");
                    toBank(bankType, questions);
                } catch (Exception e) {
                    log.error("导入异常",e);
                    if (e instanceof AppSystemException){
                        throw e;
                    }
                    throw new RuntimeException("导入异常");
                }

            }
        }

        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> importQuestionBankExcel(MultipartHttpServletRequest request, Integer bankType) throws Exception {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            MultipartFile multipartFile = entry.getValue();

            if (multipartFile instanceof CommonsMultipartFile) {

                List<Question> questions = ExcelFileUtils.importExcel(multipartFile, 0, 1, Question.class);
                toBank(bankType, questions);
            }

        }
        return new ResponseBody<>();
    }

    public void toBank(Integer bankType, List<Question> questions) {
        List<HerolandQuestionBankImportDP> dps = new ArrayList<>();
        questions.forEach(v -> {
            HerolandQuestionBankImportDP herolandQuestionBankImportDP = new HerolandQuestionBankImportDP();

            try {
                dps.add(BeanUtil.insertConversion(v, herolandQuestionBankImportDP));
                herolandQuestionBankImportDP.setBankType(bankType);
            } catch (Exception e) {
                log.error("e", e);
            }

            herolandQuestionBankImportDP.setImportId(v.getId());


            String gradeid = v.getGradeid();
            String subjectid = v.getSubjectid();
            herolandQuestionBankImportDP.setGradeid(transfer("GA", gradeid));
            herolandQuestionBankImportDP.setSubjectid(transfer("CU", subjectid));

            try {
                herolandQuestionBankImportDP.setDiff(Integer.valueOf(v.getDiff()));
            } catch (NumberFormatException ignored) {
            }
            try {
                herolandQuestionBankImportDP.setPassageid(Long.valueOf(v.getPassageid()));
            } catch (NumberFormatException ignored) {
            }
            try {
                herolandQuestionBankImportDP.setQtype(Integer.parseInt(v.getQtype()));
            } catch (NumberFormatException ignored) {
            }
            try {
                herolandQuestionBankImportDP.setThink(Integer.parseInt(v.getThink()));
            } catch (NumberFormatException ignored) {
            }
            checkBankFromImport(herolandQuestionBankImportDP);
            List<Long> knowledgeIds = handleKnowlwdgeFromImportQuestion(herolandQuestionBankImportDP);
            herolandQuestionBankImportDP.setKnowledgeIds(knowledgeIds);
        });

        importQuestion(dps);
    }


    private List<Long>  handleKnowlwdgeFromImportQuestion(HerolandQuestionBankImportDP importDP){
        String knowledgeStrs = importDP.getKnowledges();
        List<Long> knowledgeIds = Lists.newArrayList();
        List<String> knowledges = Arrays.asList(knowledgeStrs.split(","));
        String grade = importDP.getGradeid();
        String course = importDP.getSubjectid();
        HerolandKnowledgeQO qo = new HerolandKnowledgeQO();
        qo.setCourse(course);
//            qo.setGrade();
        knowledges.stream().forEach(k -> {
            qo.setKnowledge(k);
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByQuery2(qo);
            if (CollectionUtils.isEmpty(herolandKnowledges)){
                knowledgeIds.add(createSimpleHerolandKnowledge(grade,course,importDP.getDiff(),k));
            }else {

                List<HerolandKnowledge> collect = herolandKnowledges.stream().filter(s -> Objects.equals(s.getGrade(), grade)).collect(Collectors.toList());
                List<HerolandKnowledge> collect2 = herolandKnowledges.stream().filter(s -> org.apache.commons.lang3.StringUtils.isBlank(s.getGrade())).collect(Collectors.toList());
                if (collect.size() > 0){
                    collect.stream().forEach(kk -> {
                        if (NumberUtils.nullOrZero(kk.getDiff())){
                            kk.setDiff(importDP.getDiff());
                            herolandKnowledgeMapper.updateByPrimaryKeySelective(kk);
                        }
                    });
                    Optional<HerolandKnowledge> first = collect.stream().filter(s -> Objects.equals(s.getDiff(), importDP.getDiff())).findFirst();
                    if (first.isPresent()){
                        knowledgeIds.add(first.get().getId());
                    }else {
                        knowledgeIds.add(createSimpleHerolandKnowledge(grade,course,importDP.getDiff(),k));
                    }
                }else{
                    if (collect2.size() > 0){
                        collect2.stream().forEach(kk -> {
                            if (NumberUtils.nullOrZero(kk.getDiff())){
                                kk.setDiff(importDP.getDiff());
                            }
                            kk.setGrade(grade);
                            herolandKnowledgeMapper.updateByPrimaryKeySelective(kk);
                        });

                        Optional<HerolandKnowledge> first = collect2.stream().filter(s -> Objects.equals(s.getDiff(), importDP.getDiff())).findFirst();
                        if (first.isPresent()){
                            knowledgeIds.add(first.get().getId());
                        }else {
                            knowledgeIds.add(createSimpleHerolandKnowledge(grade,course,importDP.getDiff(),k));
                        }

                    }else {
                        knowledgeIds.add(createSimpleHerolandKnowledge(grade,course,importDP.getDiff(),k));
                    }
                }
            }
        });
        return knowledgeIds;
    }

    private Long createSimpleHerolandKnowledge(String grade, String course, Integer diff, String content){
        HerolandKnowledge knowledge1 = new HerolandKnowledge();
        knowledge1.setGrade(grade);
        knowledge1.setCourse(course);
        knowledge1.setKnowledge(content);
        knowledge1.setDiff(diff);
        herolandKnowledgeMapper.insertSelective(knowledge1);
        return knowledge1.getId();
    }

    private void checkBankFromImport(HerolandQuestionBankImportDP importDP){
        if (StringUtils.isEmpty(importDP.getQuestion())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TITLE.getErrorMessage());
        }
        AssertUtils.notNull(importDP.getQtype(), HerolandErrMsgEnum.ERROR_TYPE.getErrorMessage());
        AssertUtils.notNull(importDP.getAnswer1(), HerolandErrMsgEnum.ERROR_ANSWER.getErrorMessage());
        if (QtTypeEnum.OPTION.equals(importDP.getQtype())){
            boolean empty = StringUtils.isEmpty(importDP.getOption_a()) && StringUtils.isEmpty(importDP.getOption_b())
                    && StringUtils.isEmpty(importDP.getOption_c()) && StringUtils.isEmpty(importDP.getOption_d())
                    && StringUtils.isEmpty(importDP.getOption_e());
            if (empty){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_OPTION.getErrorMessage());
            }
        }
    }


    private PageResponse<HeroLandQuestionBankListForTopicDto> questionListForChapter(HerolandQuestionBankListForChapterRequest request) {
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);
        List<HerolandKnowledgeRefer> data = herolandKnowledgeReferMapper.selectByReferIds(request.getChapterIds(), KnowledgeReferEnum.CHAPTER.getType());

        Map<Long, List<HerolandKnowledgeRefer>> knowledgeMap = data.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getKnowledgeId));

        List<Long> knowledges = data.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(knowledges)) {
            return pageResult;
        }
        HerolandQuestionSelectQo qo = BeanCopyUtils.copyByJSON(request, HerolandQuestionSelectQo.class);
        //兼容
        if (!StringUtils.isEmpty(qo.getCourse())){
            if (CollectionUtils.isEmpty(qo.getCourseList())){
                qo.setCourseList(Lists.newArrayList());
            }
            qo.getCourseList().add(qo.getCourse());
            qo.setCourseList(qo.getCourseList().stream().distinct().collect(Collectors.toList()));
        }
        resolveYearRange(request.getYearRange(), qo);
        if (!StringUtils.isEmpty(request.getYear())) {
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
        List<HerolandKnowledgeRefer> bankRefers = herolandKnowledgeReferMapper.selectByKnowledgeIds(knowledges, KnowledgeReferEnum.QUESTION.getType());
        if (CollectionUtils.isEmpty(bankRefers)) {
            return pageResult;
        }
        List<Long> bankReferIds = bankRefers.stream().map(HerolandKnowledgeRefer::getReferId).collect(Collectors.toList());
        Map<Long, List<HerolandKnowledgeRefer>> bankIdKnowledgeMap = bankRefers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));

        qo.setBankIds(bankReferIds);
        Page<HerolandQuestionBank> banks = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.questionSelect(qo));

        pageResult = build(banks);
        if (!CollectionUtils.isEmpty(pageResult.getItems())) {
            pageResult.getItems().stream().forEach(e -> {
                List<Long> chapterIds = Lists.newArrayList();
                if (bankIdKnowledgeMap.containsKey(e.getId())) {
                    List<HerolandKnowledgeRefer> refers = bankIdKnowledgeMap.get(e.getId());
                    List<Long> ks = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
                    ks.stream().forEach(k -> {
                        if (knowledgeMap.containsKey(k)) {
                            chapterIds.addAll(knowledgeMap.get(k).stream().map(HerolandKnowledgeRefer::getReferId).collect(Collectors.toList()));
                        }
                    });
                }
                e.setChapterId(chapterIds.stream().distinct().collect(Collectors.toList()));
            });
        }
        return pageResult;
    }

    private PageResponse<HeroLandQuestionBankListForTopicDto> questionListForCourse(HerolandQuestionBankListForChapterRequest request) {
        HerolandQuestionSelectQo qo = BeanCopyUtils.copyByJSON(request, HerolandQuestionSelectQo.class);
        //兼容
        if (!StringUtils.isEmpty(qo.getCourse())){
            if (CollectionUtils.isEmpty(qo.getCourseList())){
                qo.setCourseList(Lists.newArrayList());
            }
            qo.getCourseList().add(qo.getCourse());
            qo.setCourseList(qo.getCourseList().stream().distinct().collect(Collectors.toList()));
        }
        resolveYearRange(request.getYearRange(), qo);
        if (!StringUtils.isEmpty(request.getYear())) {
            Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
        Page<HerolandQuestionBank> banks = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandQuestionBankMapper.questionSelect(qo));
        return build(banks);
    }

    private PageResponse<HeroLandQuestionBankListForTopicDto> build(Page<HerolandQuestionBank> banks) {
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionBankListForTopicDto> list = new ArrayList<>();
        pageResult.setItems(list);

        if (CollectionUtils.isEmpty(banks.getResult())) {
            return pageResult;
        }
        List<String> courseKeys = banks.getResult().stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.getResult().stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionBankListForTopicDto> chapterDtos = Lists.newArrayList();
        List<Long> bankIds = banks.getResult().stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());

        Map<Long, List<HerolandQuestionBank>> bankIdMap = banks.getResult().stream().collect(Collectors.groupingBy(HerolandQuestionBank::getId));


        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));

        banks.getResult().stream().forEach(e -> {
            HeroLandQuestionBankListForTopicDto dto = new HeroLandQuestionBankListForTopicDto();
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
            dto.setBankType(e.getBankType());
            dto.setYear(StringUtils.isEmpty(e.getYear()) ? "" : DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            dto.setQtId(e.getQtId());
            if (qtMap.containsKey(e.getId())) {
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

    private void resolveYearRange(Integer yearRange, HerolandQuestionSelectQo qo) {
        int num = 0;
        if (!NumberUtils.nullOrZero(yearRange)) {
            QtYearRangeEnum qtYearRangeEnum = QtYearRangeEnum.valueOfLevel(yearRange);
            if (qtYearRangeEnum == null) {
                num = -999;
            } else if (QtYearRangeEnum.ONE.getType().equals(yearRange)) {
                num = 0;
            } else if (QtYearRangeEnum.THREE.getType().equals(yearRange)) {
                num = -2;
            } else if (QtYearRangeEnum.FIVE.getType().equals(yearRange)) {
                num = -4;
            }
            Date now = new Date();
            Date beginDate = DateUtils.plusDate(now, num, TimeIntervalUnit.YEAR);
            String nowYear = DateUtils.dateToYear(now);
            String beginYear = DateUtils.dateToYear(beginDate);

            Date beginTime = DateUtils.string2Date(beginYear + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
            Date endTime = DateUtils.string2Date(nowYear + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
            qo.setBeginTime(beginTime);
            qo.setEndTime(endTime);
        }
    }

    private HeroLandQuestionBankDto getAdminData(HerolandQuestionBank bank) {
        HeroLandQuestionBankDto bankDto = BeanCopyUtils.copyByJSON(bank, HeroLandQuestionBankDto.class);
        bankDto.setGrade(bank.getGradeCode());
        bankDto.setYear(StringUtils.isEmpty(bank.getYear()) ? "" : DateUtils.dateToYear(bank.getYear()));
        List<String> keys = Lists.newArrayList();
        keys.add(bank.getCourse());
        keys.add(bank.getGradeCode());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(bank.getCourse())) {
            bankDto.setCourseName(keyMap.get(bank.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(bankDto.getGrade())) {
            bankDto.setGradeName(keyMap.get(bankDto.getGrade()).get(0).getDictValue());
        }
        return bankDto;
    }



    public String transfer(String code, String mappingId){
        if (StringUtils.isEmpty(mappingId)){
            return "";
        }

        if (mappingId.contains(code)){
            return mappingId;
        }

        if (code.equalsIgnoreCase("GA")){

            //说明是必修选修
            if (mappingId.length() > 3){
                log.info("必修选修的年级");
                List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, mappingId);
                if (CollectionUtils.isEmpty(herolandBasicData)){
                    return "";
                }else {
                    return herolandBasicData.get(0).getDictKey();
                }
            }
            List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, null);
            if (CollectionUtils.isEmpty(herolandBasicData)){
                return "";
            }
            Optional<HerolandBasicData> first = herolandBasicData.stream().filter(e -> {
                if (e.getMappingId().charAt(0) == mappingId.charAt(0) && e.getMappingId().charAt(1) == mappingId.charAt(1)) {
                    return true;
                } else {
                    return false;
                }
            }).findFirst();
            if (first.isPresent()){
                return first.get().getDictKey();
            }else {
                return "";
            }
        }
        List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, mappingId);
        if (CollectionUtils.isEmpty(herolandBasicData) || herolandBasicData.size() > 1){
            return "";
        }else {
            return herolandBasicData.get(0).getDictKey();
        }
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
