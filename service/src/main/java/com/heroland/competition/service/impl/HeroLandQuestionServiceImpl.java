package com.heroland.competition.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.constants.*;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.*;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.*;
import com.heroland.competition.domain.request.*;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandCourseService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.domain.dp.PlatformSysUserClassDP;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mac
 */
@Service
public class HeroLandQuestionServiceImpl implements HeroLandQuestionService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandTopicGroupServiceImpl.class);

    @Resource
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Resource
    private HerolandTopicQuestionMapper herolandTopicQuestionMapper;

    @Resource
    private HerolandQuestionBankMapper herolandQuestionBankMapper;

    @Resource
    private HerolandKnowledgeReferMapper herolandKnowledgeReferMapper;

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Resource
    private HerolandChapterMapper heroLandChapterMapper;

    @Resource
    private HerolandQuestionBankDetailMapper herolandQuestionBankDetailMapper;


    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @Resource
    private HerolandTopicGroupPartService herolandTopicGroupPartService;

    @Resource
    private HerolandSchoolCourseMapper herolandSchoolCourseMapper;
    @Resource
    private HerolandTopicJoinUserMapper herolandTopicJoinUserMapper;
    @Resource
    private HeroLandQuestionRecordDetailMapper heroLandQuestionRecordDetailMapper;

    @Resource
    private HerolandCourseMapper herolandCourseMapper;
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    private static Random random = new Random();


    @Override
    public Boolean addQuestion(HeroLandQuestionDP dp) {
        return null;
    }

    @Override
    @Transactional
    public Boolean editTopic(HeroLandTopicGroupDP dp) {
        checkBeforeUpdateEditTopic(dp);
        if (StringUtils.isAnyBlank(dp.getOrgCode(), dp.getTopicName())) {
            ResponseBodyWrapper.failParamException();
        }
        HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
        return heroLandTopicGroupMapper.updateByPrimaryKeySelective(heroLandTopicGroup) > 0;
    }

    private void checkBeforeUpdateEditTopic(HeroLandTopicGroupDP dp){
        dp.updateCheck();
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(dp.getId());
        if (!Objects.equals(heroLandTopicGroup.getType(), dp.getType())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_UPDATE_PARAM_TYPE.getErrorMessage());
        }
        Date now = new Date();
        if (heroLandTopicGroup.getStartTime().before(now)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_UPDATE_PARAM_BEGIN.getErrorMessage());
        }
        if (heroLandTopicGroup.getEndTime().before(now)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_UPDATE_PARAM_END.getErrorMessage());
        }
        //世界赛在开始报名前不能修改，因为有可能会改变人数限制
        if (Objects.equals(heroLandTopicGroup.getType(), TopicTypeConstants.WORLD_COMPETITION)){
            if (heroLandTopicGroup.getRegisterBeginTime().before(now)){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_UPDATE_PARAM_REGISTER.getErrorMessage());
            }
        }

    }

    @Override
    @Transactional
    public Boolean deleteTopic(Long id) {

        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(id);
        if(heroLandTopicGroup == null){
            return false;
        }
        Date now = new Date();
        if (Objects.equals(heroLandTopicGroup.getType(), TopicTypeConstants.WORLD_COMPETITION)){
            if (heroLandTopicGroup.getRegisterBeginTime().before(now)){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_DELETE_PARAM_REGISTER.getErrorMessage());
            }

            if (heroLandTopicGroup.getRegisterBeginTime().getTime() - now.getTime() < 5*60*1000){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_DELETE_PARAM_REGISTER2.getErrorMessage());
            }
        }
        heroLandTopicGroupMapper.deleteByPrimaryKey(id);
        herolandTopicQuestionMapper.deleteByTopicIds(Lists.newArrayList(id));
        return true;
    }

    @Override
    public Long addTopic(HeroLandTopicGroupDP dp) {
        if (!NumberUtils.nullOrZeroLong(dp.getId())) {
            editTopic(dp);
            return dp.getId();
        }
        dp = dp.addCheckAndInit();
        HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
        heroLandTopicGroupMapper.insertSelective(heroLandTopicGroup);
        return heroLandTopicGroup.getId();
    }

    @Override
    @Transactional
    public Long addTopicForS(HeroLandTopicAddDepartmentRequest request) {
        AssertUtils.notNull(request.getType());
       if (request.getType().equals(TopicTypeConstants.WORLD_COMPETITION)){
           return addTopicForWorld(request);
       }else {
           return addTopicForSchool(request);
       }
    }


    private Long addTopicForSchool(HeroLandTopicAddDepartmentRequest request){
        Long id = null;
        HeroLandTopicGroupDP dp = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupDP.class);
        dp.setDescription(request.getDesc());
        if (NumberUtils.nullOrZeroLong(request.getId())){
            //新增
            dp = dp.addCheckAndInit();
            HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
            heroLandTopicGroupMapper.insertSelective(heroLandTopicGroup);
            id = heroLandTopicGroup.getId();
        }else {
            //编辑
            checkBeforeUpdateEditTopic(dp);
            HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
            heroLandTopicGroupMapper.updateByPrimaryKeySelective(heroLandTopicGroup);
            id = request.getId();
            herolandTopicGroupPartService.deleteDepartmentBytopicIds(Lists.newArrayList(id));
        }
        Long topicId = id;
        if (!CollectionUtils.isEmpty(request.getSchoolCourses())){
            List<HerolandTopicGroupPartDP> list = Lists.newArrayList();
            request.getSchoolCourses().stream().forEach(e -> {
                HerolandTopicGroupPartDP partDP = BeanCopyUtils.copyByJSON(e, HerolandTopicGroupPartDP.class);
                partDP.setTopicId(topicId);
                list.add(partDP);
            });
            herolandTopicGroupPartService.addBatchDepartment(list);
        }
        return topicId;
    }
    private Long addTopicForWorld(HeroLandTopicAddDepartmentRequest request){

        Long id = null;
        HeroLandTopicGroupDP dp = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupDP.class);
        dp.setDescription(request.getDesc());

        if (dp.getRegisterBeginTime() == null || dp.getRegisterEndTime() == null || dp.getRegisterBeginTime().after(dp.getStartTime()) || dp.getRegisterEndTime().after(dp.getStartTime())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }
        if (dp.getRegisterEndTime().after(dp.getStartTime())){
            dp.setRegisterEndTime(dp.getStartTime());
        }
        checkMultiWorldComIn10Min(request);
        if (NumberUtils.nullOrZeroLong(request.getId())){
            //新增
            dp = dp.addCheckAndInit();
            HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
            heroLandTopicGroupMapper.insertSelective(heroLandTopicGroup);
            id = heroLandTopicGroup.getId();
        }else {
            //编辑
            checkBeforeUpdateEditTopic(dp);
            HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
            heroLandTopicGroupMapper.updateByPrimaryKeySelective(heroLandTopicGroup);
            id = request.getId();
            herolandTopicGroupPartService.deleteDepartmentBytopicIds(Lists.newArrayList(id));
            herolandTopicQuestionMapper.deleteByTopicIds(Lists.newArrayList(id));
        }
        Long topicId = id;
        if (!CollectionUtils.isEmpty(request.getGradeCoursesForWorld())){
            List<HerolandTopicGroupPartDP> list = Lists.newArrayList();
            request.getGradeCoursesForWorld().stream().forEach(e -> {
                HerolandTopicGroupPartDP partDP = BeanCopyUtils.copyByJSON(e, HerolandTopicGroupPartDP.class);
                partDP.setTopicId(topicId);
                list.add(partDP);
            });
            herolandTopicGroupPartService.addBatchDepartment(list);
        }
        if (!CollectionUtils.isEmpty(request.getQuestionIds())){
            List<HerolandTopicQuestion> list = new ArrayList<>();
            request.getQuestionIds().stream().distinct().forEach(e -> {
                HerolandTopicQuestion topicQuestion = new HerolandTopicQuestion();
                topicQuestion.setQuestionId(e);
                topicQuestion.setTopicId(topicId);
                topicQuestion.setChapterId(0L);
                topicQuestion.setOrgCode(request.getOrgCode());
                list.add(topicQuestion);
            });
            herolandTopicQuestionMapper.saveBatch(list);
        }
        return topicId;
    }

    private void checkMultiWorldComIn10Min(HeroLandTopicAddDepartmentRequest request){
        Date now = new Date();
        Date beginTime = request.getStartTime();
        AssertUtils.notEmpty(request.getGradeCoursesForWorld());
        String grade = request.getGradeCoursesForWorld().get(0).getGradeCode();
        HeroLandTopicGroupExample heroLandTopicGroupExample = new HeroLandTopicGroupExample();
        HeroLandTopicGroupExample.Criteria criteria = heroLandTopicGroupExample.createCriteria();
        criteria.andEndTimeLessThan(beginTime).andStartTimeGreaterThan(now).andTypeEqualTo(TopicTypeConstants.WORLD_COMPETITION);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(heroLandTopicGroupExample);
        if(CollectionUtils.isEmpty(heroLandTopicGroups)){
          return;
        }
        List<Long> topicIds = heroLandTopicGroups.stream().filter(e -> beginTime.getTime() - e.getEndTime().getTime() < 10 * 60 * 1000).map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(topicIds)){
            HerolandTopicForSQO sqo = new HerolandTopicForSQO();
            sqo.setTopicIds(topicIds);
            List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
            if (!CollectionUtils.isEmpty(herolandTopicGroupPartDPS)){
                long count = herolandTopicGroupPartDPS.stream().filter(part -> Objects.equals(part.getGradeCode(), grade)).count();
                if (count > 0L){
                    ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME_PARAM.getErrorMessage());
                }
            }
        }
    }

    @Override
    public PageResponse<HeroLandQuestionListForTopicDto> getTopicQuestions(HeroLandTopicQuestionsPageRequest request) {
        PageResponse<HeroLandQuestionListForTopicDto> pageResult = new PageResponse<>();
        //校正参数,前端默认所有的接口都加了orgCode，导致在查询世界赛和校际赛时这个参数参与数据库查询，结果为空
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByPrimaryKeys(request.getTopicIds());
        if(!CollectionUtils.isEmpty(heroLandTopicGroups) &&
                (Objects.equals(heroLandTopicGroups.get(0).getType(), TopicTypeConstants.IntegerER_SCHOOL_COMPETITION) || Objects.equals(heroLandTopicGroups.get(0).getType(), TopicTypeConstants.WORLD_COMPETITION))){
            request.setCourseCode(null);
            request.setGradeCode(null);
            request.setClassCode(null);
            request.setOrgCode(null);
        }

        HerolandTopicQuestionQo herolandTopicQuestionQo = BeanCopyUtils.copyByJSON(request, HerolandTopicQuestionQo.class);
        herolandTopicQuestionQo.setType(request.getTopicType());
        Page<QuestionTopicDP> questions = PageHelper.startPage(herolandTopicQuestionQo.getPageIndex(), herolandTopicQuestionQo.getPageSize(), true).doSelectPage(
                () -> herolandTopicQuestionMapper.selectQuestionsByTopic(herolandTopicQuestionQo));
        if (CollectionUtils.isEmpty(questions.getResult())) {
            return pageResult;
        }

        List<Long> questionIds = questions.getResult().stream().map(QuestionTopicDP::getQuestionId).collect(Collectors.toList());
        Map<Long, List<QuestionTopicDP>> topicIdQuestionMap = questions.getResult().stream().collect(Collectors.groupingBy(QuestionTopicDP::getTopicId));
        List<HerolandQuestionBank> banks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);
        List<String> courseKeys = banks.stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionListForTopicDto> topicDtos = Lists.newArrayList();
        List<String> qtIds = banks.stream().map(HerolandQuestionBank::getQtId).collect(Collectors.toList());
        List<Long> bankIds = banks.stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());
        List<HerolandQuestionUniqDP> uniqDPS = herolandQuestionBankMapper.selectSimpleSnaphot(qtIds);
        Map<String, List<HerolandQuestionUniqDP>> qtSnapshotMap = uniqDPS.stream().collect(Collectors.groupingBy(HerolandQuestionUniqDP::getQtId));
        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));

        List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(bankIds, KnowledgeReferEnum.QUESTION.getType());
        Map<Long, List<HerolandKnowledgeRefer>> konwlegeReferMap = Maps.newHashMap();
        Map<Long, List<HerolandKnowledge>> konwlegeMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(refers)){
            List<Long> konwlegeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).collect(Collectors.toList());
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(konwlegeIds);
            konwlegeReferMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));
            konwlegeMap = herolandKnowledges.stream().collect(Collectors.groupingBy(HerolandKnowledge::getId));
        }

        Map<Long, List<HerolandKnowledgeRefer>> konwlegeReferV2Map = konwlegeReferMap;
        Map<Long, List<HerolandKnowledge>> konwlegeV2Map = konwlegeMap;


        banks.stream().forEach(e -> {
            HeroLandQuestionListForTopicDto dto = new HeroLandQuestionListForTopicDto();
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
            dto.setYear(org.springframework.util.StringUtils.isEmpty(e.getYear()) ? "" : DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            dto.setQtId(e.getQtId());
            if (qtSnapshotMap.containsKey(e.getQtId())) {
                HerolandQuestionUniqDP max = qtSnapshotMap.get(e.getQtId()).stream().max(Comparator.comparing(HerolandQuestionUniqDP::getSnapshotNo)).get();
                if (!Objects.equals(max.getSnapshotNo(), e.getSnapshotNo())) {
                    dto.setNewQuestionId(max.getId());
                    dto.setDesc("UPDATED");
                } else {
                    dto.setDesc("NORMAL");
                }
            } else {
                dto.setDesc("DELETED");
            }
            if (qtMap.containsKey(e.getId())) {
                dto.setAnswer(qtMap.get(e.getId()).get(0).getAnswer());
                dto.setOptionAnswer(qtMap.get(e.getId()).get(0).getOptionAnswer());
                dto.setAnalysis(qtMap.get(e.getId()).get(0).getAnalysis());
                List<QuestionOptionDto> questionOptionDto = JSON.parseArray(qtMap.get(e.getId()).get(0).getOption(), QuestionOptionDto.class);
                dto.setOptions(questionOptionDto);
            }
            if (konwlegeReferV2Map.containsKey(e.getId())){
                konwlegeReferV2Map.get(e.getId()).stream().forEach(k -> {
                    if (konwlegeV2Map.containsKey(k.getKnowledgeId())){
                        HerolandKnowledgeSimpleDto simpleDto = new HerolandKnowledgeSimpleDto();
                        simpleDto.setKnowledge(konwlegeV2Map.get(k.getKnowledgeId()).get(0).getKnowledge());
                        dto.getKnowledges().add(simpleDto);
                    }
                });
            }
            topicDtos.add(dto);

        });
        List<HeroLandQuestionListForTopicDto> finalTopicDtos = Lists.newArrayList();
        for (Map.Entry<Long, List<QuestionTopicDP>> entry : topicIdQuestionMap.entrySet()) {
            List<Long> subQuestionIds = entry.getValue().stream().map(QuestionTopicDP::getQuestionId).collect(Collectors.toList());
            QuestionTopicDP questionTopicDP = entry.getValue().get(0);
            topicDtos.stream().forEach(e -> {
                if (subQuestionIds.contains(e.getId())) {
                    HeroLandQuestionListForTopicDto newTopicDto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionListForTopicDto.class);
                    newTopicDto.setTopicId(entry.getKey());
                    newTopicDto.setTopicName(questionTopicDP.getTopicName());
                    newTopicDto.setTopicDiff(questionTopicDP.getTopicDiff());
                    newTopicDto.setTopicType(questionTopicDP.getTopicType());
                    newTopicDto.setTopicLevelCode(questionTopicDP.getTopicLevelCode());
                    newTopicDto.setTopicOrgCode(questionTopicDP.getOrgCode());
                    newTopicDto.setTopicGradeCode(questionTopicDP.getTopicGradeCode());
                    newTopicDto.setTopicClassCode(questionTopicDP.getClassCode());
                    newTopicDto.setTopicCourseCode(questionTopicDP.getCourseCode());
                    finalTopicDtos.add(newTopicDto);
                }
            });
        }

        pageResult.setItems(finalTopicDtos);
        pageResult.setPageSize(questions.getPageSize());
        pageResult.setPage(questions.getPageNum());
        pageResult.setTotal((int) questions.getTotal());
        return pageResult;

    }

    @Override
    public Boolean saveAssign(HeroLandTopicAssignRequest request) {

        herolandTopicQuestionMapper.deleteByTopicIds(Lists.newArrayList(request.getTopicId()));

        List<HerolandTopicQuestion> list = new ArrayList<>();
        request.getQuestionIds().stream().forEach(e -> {
            HerolandTopicQuestion topicQuestion = new HerolandTopicQuestion();
            topicQuestion.setQuestionId(e);
            topicQuestion.setTopicId(request.getTopicId());
            topicQuestion.setChapterId(0L);
            topicQuestion.setOrgCode(request.getOrgCode());
            list.add(topicQuestion);
        });
        request.getChapterQuestions().stream().forEach(e -> {
            e.getQuestionIds().stream().forEach(ques -> {
                HerolandTopicQuestion topicQuestion = new HerolandTopicQuestion();
                topicQuestion.setQuestionId(ques);
                topicQuestion.setTopicId(request.getTopicId());
                topicQuestion.setChapterId(e.getChapterId());
                topicQuestion.setOrgCode(request.getOrgCode());
                list.add(topicQuestion);
            });
        });
        List<HerolandTopicQuestion> uniq = list.stream().distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(uniq)) {
            return herolandTopicQuestionMapper.saveBatch(uniq) > 0;
        }
        return false;
    }

    @Override
    public HeroLandTopicDto getTopic(HeroLandTopicPageRequest request) {
        if (NumberUtils.nullOrZeroLong(request.getTopicId())) {
            return null;
        }
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(request.getTopicId());
        if (heroLandTopicGroup == null){
            return null;
        }
        HeroLandTopicDto heroLandTopicDto = BeanCopyUtils.copyByJSON(heroLandTopicGroup, HeroLandTopicDto.class);
        if(!(Objects.equals(heroLandTopicDto.getType(),TopicTypeConstants.WORLD_COMPETITION) || Objects.equals(heroLandTopicDto.getType(),TopicTypeConstants.IntegerER_SCHOOL_COMPETITION))){
            List<String> keys = Lists.newArrayList();
            keys.add(heroLandTopicDto.getCourseCode());
            keys.add(heroLandTopicDto.getGradeCode());
            keys.add(heroLandTopicDto.getClassCode());
            keys.add(heroLandTopicDto.getOrgCode());
            List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
            Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
            if (keyMap.containsKey(heroLandTopicDto.getCourseCode())) {
                heroLandTopicDto.setCourseName(keyMap.get(heroLandTopicDto.getCourseCode()).get(0).getDictValue());
            }
            if (keyMap.containsKey(heroLandTopicDto.getGradeCode())) {
                heroLandTopicDto.setGradeName(keyMap.get(heroLandTopicDto.getGradeCode()).get(0).getDictValue());
            }
            if (keyMap.containsKey(heroLandTopicDto.getClassCode())) {
                heroLandTopicDto.setClassName(keyMap.get(heroLandTopicDto.getClassCode()).get(0).getDictValue());
            }
            if (keyMap.containsKey(heroLandTopicDto.getOrgCode())) {
                heroLandTopicDto.setOrgCode(keyMap.get(heroLandTopicDto.getOrgCode()).get(0).getDictValue());
            }
        }
        if(Objects.equals(heroLandTopicDto.getType(),TopicTypeConstants.WORLD_COMPETITION)){
            getCourseForWorldDtoList(request.getTopicId(), heroLandTopicDto);
            List<HeroLandQuestionListForTopicDto> questions = getQuestions(request.getTopicId(), false);
            heroLandTopicDto.setQuestions(questions);
        }

        return heroLandTopicDto;
    }


    private void getCourseForWorldDtoList(Long topicId, HeroLandTopicDto heroLandTopicDto) {
        HerolandTopicForSQO sqo = new HerolandTopicForSQO();
        sqo.setTopicIds(Lists.newArrayList(topicId));
        List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
        if (!CollectionUtils.isEmpty(herolandTopicGroupPartDPS)) {
            List<String> courseCodeList = herolandTopicGroupPartDPS.stream().map(HerolandTopicGroupPartDP::getCourseCode).collect(Collectors.toList());
            List<String> gradeCodeList = herolandTopicGroupPartDPS.stream().map(HerolandTopicGroupPartDP::getGradeCode).collect(Collectors.toList());
            courseCodeList.addAll(gradeCodeList);
            courseCodeList = courseCodeList.stream().distinct().collect(Collectors.toList());
            List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(courseCodeList);
            Map<String, List<HerolandBasicDataDP>> adminMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
            List<HerolandTopicAddSchoolCourseForWorldDto> courseForWorldDtoList = herolandTopicGroupPartDPS.stream().map(e -> {
                HerolandTopicAddSchoolCourseForWorldDto courseForWorldDto = new HerolandTopicAddSchoolCourseForWorldDto();
                courseForWorldDto.setGradeCode(e.getGradeCode());
                courseForWorldDto.setCourseCode(e.getCourseCode());
                courseForWorldDto.setGradeName(adminMap.containsKey(e.getGradeCode()) ? adminMap.get(e.getGradeCode()).get(0).getDictValue() : null);
                courseForWorldDto.setCourseName(adminMap.containsKey(e.getClassCode()) ? adminMap.get(e.getCourseCode()).get(0).getDictValue() : null);
                return courseForWorldDto;
            }).collect(Collectors.toList());
            heroLandTopicDto.setGradeCoursesForWorld(courseForWorldDtoList);
        }

    }

    @Override
    public List<HeroLandTopicDto> getTopics(HeroLandTopicQuestionsPageRequest request) {
        HeroLandTopicGroupQO groupQO = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByQuery(groupQO);
        try {
            return BeanUtil.queryListConversion(heroLandTopicGroups, HeroLandTopicDto.class);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    @Override
    public List<TopicSimpleDto> getTopicsByTypeAndState(Integer topicType, String topicState) {
        List<TopicSimpleDto> result = Lists.newArrayList();
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByTypeAndState(topicType, topicState);
        heroLandTopicGroups.stream().forEach(e -> {
            TopicSimpleDto topicSimpleDto = new TopicSimpleDto();
            topicSimpleDto.setTopicId(e.getId());
            topicSimpleDto.setTopicType(e.getType());
            topicSimpleDto.setTopicName(e.getTopicName());
            topicSimpleDto.setStartTime(e.getStartTime());
            topicSimpleDto.setEndTime(e.getEndTime());
            result.add(topicSimpleDto);
        });
        return result;
    }

    @Override
    public List<HeroLandQuestionTopicListDto> getTopicsQuestions(HeroLandTopicQuestionsQo qo) {
        HeroLandTopicGroupQO groupQO = BeanCopyUtils.copyByJSON(qo, HeroLandTopicGroupQO.class);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByQuery(groupQO);
        if (CollectionUtils.isEmpty(heroLandTopicGroups)) {
            return Lists.newArrayList();
        }
        Map<Long, HeroLandTopicGroup> topicGroupMap = heroLandTopicGroups.stream().collect(Collectors.toMap(HeroLandTopicGroup::getId, Function.identity()));
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(qo.getTopicIds(), null);

        if (CollectionUtils.isEmpty(herolandTopicQuestions)) {
            return getTopicsQuestionsByQO(qo);
        }
        List<HeroLandQuestionTopicListDto> result = Lists.newArrayList();
        List<Long> questionIds = herolandTopicQuestions.stream().map(HerolandTopicQuestion::getQuestionId).collect(Collectors.toList());
        Map<Long, Long> questionIdByTopicId = herolandTopicQuestions.stream().collect(Collectors.toMap(HerolandTopicQuestion::getQuestionId, HerolandTopicQuestion::getTopicId, (o, n) -> n));
        List<HerolandQuestionBank> banks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);

        List<String> courseKeys = banks.stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        List<String> storage = banks.stream().map(HerolandQuestionBank::getStorage).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        courseKeys.addAll(storage);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionListForTopicDto> topicDtos = Lists.newArrayList();
        List<Long> bankIds = banks.stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = Maps.newHashMap();
        if (Objects.equals(qo.getIncludeDetail(), Boolean.TRUE)) {
            List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
            qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));
        }
        for (HerolandQuestionBank e : banks) {
            HeroLandQuestionListForTopicDto dto = new HeroLandQuestionListForTopicDto();
            dto.setGrade(e.getGradeCode());
            dto.setCourse(e.getCourse());
            dto.setStorage(e.getStorage());
            dto.setCourseName(keysMap.containsKey(e.getCourse()) ? keysMap.get(e.getCourse()).get(0).getDictValue() : "");
            dto.setGradeName(keysMap.containsKey(e.getGradeCode()) ? keysMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            dto.setStorageName(keysMap.containsKey(e.getStorage()) ? keysMap.get(e.getStorage()).get(0).getDictValue() : "");
            dto.setArea(e.getArea());
            dto.setDiff(e.getDiff());
            dto.setPaperType(e.getPaperType());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setSubType(e.getSubType());
            dto.setSource(e.getSource());
            dto.setYear(org.springframework.util.StringUtils.isEmpty(e.getYear()) ? "" : DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            dto.setQtId(e.getQtId());
            dto.setThink(e.getThink());
            dto.setTopicId(questionIdByTopicId.get(Long.valueOf(e.getId())));
            if (qtMap.containsKey(e.getId())) {
                dto.setAnswer(qtMap.get(e.getId()).get(0).getAnswer());
                dto.setOptionAnswer(qtMap.get(e.getId()).get(0).getOptionAnswer());
                List<QuestionOptionDto> questionOptionDto = JSON.parseArray(qtMap.get(e.getId()).get(0).getOption(), QuestionOptionDto.class);
                dto.setOptions(questionOptionDto);
                dto.setInformation(qtMap.get(e.getId()).get(0).getInformation());
                dto.setStormAnswer(qtMap.get(e.getId()).get(0).getStormAnswer());
                dto.setAnalysis(qtMap.get(e.getId()).get(0).getAnalysis());
            }
            topicDtos.add(dto);
        }
        Map<Long, List<HeroLandQuestionListForTopicDto>> questionMap = topicDtos.stream().collect(Collectors.groupingBy(HeroLandQuestionListForTopicDto::getId));
        Map<Long, List<HerolandTopicQuestion>> topicMap = herolandTopicQuestions.stream().collect(Collectors.groupingBy(HerolandTopicQuestion::getTopicId));
        for (Map.Entry<Long, List<HerolandTopicQuestion>> entry : topicMap.entrySet()) {
            HeroLandQuestionTopicListDto topicDto = new HeroLandQuestionTopicListDto();
            topicDto.setId(entry.getKey());
            if (topicGroupMap.containsKey(topicDto.getId())) {
                BeanUtils.copyProperties(topicGroupMap.get(topicDto.getId()), topicDto);
                List<String> codes = Lists.newArrayList();
                codes.add(topicDto.getCourseCode());
                codes.add(topicDto.getGradeCode());
                codes.add(topicDto.getOrgCode());
                codes.add(topicDto.getClassCode());
                List<HerolandBasicDataDP> dataDPS = heroLandAdminService.getDictInfoByKeys(codes);
                Map<String, List<HerolandBasicDataDP>> dataMap = dataDPS.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
                topicDto.setCourseName(dataMap.containsKey(topicDto.getCourseCode()) ? keysMap.get(topicDto.getCourseCode()).get(0).getDictValue() : "");
                topicDto.setGradeName(dataMap.containsKey(topicDto.getGradeCode()) ? keysMap.get(topicDto.getGradeCode()).get(0).getDictValue() : "");
                topicDto.setClassName(dataMap.containsKey(topicDto.getClassCode()) ? keysMap.get(topicDto.getClassCode()).get(0).getDictValue() : "");
                topicDto.setOrgName(dataMap.containsKey(topicDto.getOrgCode()) ? keysMap.get(topicDto.getOrgCode()).get(0).getDictValue() : "");
            }
            List<HeroLandQuestionListForTopicDto> subQuestions = Lists.newArrayList();
            entry.getValue().forEach(e -> {
                if (questionMap.containsKey(e.getQuestionId())) {
                    subQuestions.addAll(questionMap.get(e.getQuestionId()));
                }
            });
            topicDto.setQuestions(subQuestions);
            result.add(topicDto);
        }

        return result;
    }

    private List<HeroLandQuestionListForTopicDto> getQuestions(Long topicId, boolean includeDetail){
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(Lists.newArrayList(topicId), null);

        List<HeroLandQuestionTopicListDto> result = Lists.newArrayList();
        List<Long> questionIds = herolandTopicQuestions.stream().map(HerolandTopicQuestion::getQuestionId).collect(Collectors.toList());
        Map<Long, Long> questionIdByTopicId = herolandTopicQuestions.stream().collect(Collectors.toMap(HerolandTopicQuestion::getQuestionId, HerolandTopicQuestion::getTopicId, (o, n) -> n));
        List<HerolandQuestionBank> banks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);

        List<String> courseKeys = banks.stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        List<String> storage = banks.stream().map(HerolandQuestionBank::getStorage).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        courseKeys.addAll(storage);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionListForTopicDto> topicDtos = Lists.newArrayList();
        List<Long> bankIds = banks.stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = Maps.newHashMap();
        if (Objects.equals(includeDetail, Boolean.TRUE)) {
            List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
            qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));
        }
        for (HerolandQuestionBank e : banks) {
            HeroLandQuestionListForTopicDto dto = new HeroLandQuestionListForTopicDto();
            dto.setGrade(e.getGradeCode());
            dto.setCourse(e.getCourse());
            dto.setStorage(e.getStorage());
            dto.setCourseName(keysMap.containsKey(e.getCourse()) ? keysMap.get(e.getCourse()).get(0).getDictValue() : "");
            dto.setGradeName(keysMap.containsKey(e.getGradeCode()) ? keysMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            dto.setStorageName(keysMap.containsKey(e.getStorage()) ? keysMap.get(e.getStorage()).get(0).getDictValue() : "");
            dto.setArea(e.getArea());
            dto.setDiff(e.getDiff());
            dto.setPaperType(e.getPaperType());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setSubType(e.getSubType());
            dto.setSource(e.getSource());
            dto.setYear(org.springframework.util.StringUtils.isEmpty(e.getYear()) ? "" : DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            dto.setQtId(e.getQtId());
            dto.setThink(e.getThink());
            dto.setTopicId(questionIdByTopicId.get(Long.valueOf(e.getId())));
            if (qtMap.containsKey(e.getId())) {
                dto.setAnswer(qtMap.get(e.getId()).get(0).getAnswer());
                dto.setOptionAnswer(qtMap.get(e.getId()).get(0).getOptionAnswer());
                List<QuestionOptionDto> questionOptionDto = JSON.parseArray(qtMap.get(e.getId()).get(0).getOption(), QuestionOptionDto.class);
                dto.setOptions(questionOptionDto);
                dto.setInformation(qtMap.get(e.getId()).get(0).getInformation());
                dto.setStormAnswer(qtMap.get(e.getId()).get(0).getStormAnswer());
                dto.setAnalysis(qtMap.get(e.getId()).get(0).getAnalysis());
            }
            topicDtos.add(dto);
        }
        return topicDtos;
    }



    private List<HeroLandQuestionTopicListDto> getTopicsQuestionsByQO(HeroLandTopicQuestionsQo qo) {
        HeroLandTopicGroupQO groupQO = BeanCopyUtils.copyByJSON(qo, HeroLandTopicGroupQO.class);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByQuery(groupQO);
        if (CollectionUtils.isEmpty(heroLandTopicGroups)) {
            return Lists.newArrayList();
        }


        return null;
    }


    @Override
    public List<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForCourseStatistics(HeroLandTopicQuestionForCourseRequest request) {

        List<HeroLandQuestionTopicListForStatisticDto> list = new ArrayList<>();
        HeroLandTopicGroupQO qo = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByQuery(qo);
        if (CollectionUtils.isEmpty(heroLandTopicGroups)) {
            return list;
        }
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(topicIds, null);
        Map<Long, List<HerolandTopicQuestion>> topicMap = herolandTopicQuestions.stream().collect(Collectors.groupingBy(HerolandTopicQuestion::getTopicId));
        heroLandTopicGroups.stream().forEach(e -> {
            HeroLandQuestionTopicListForStatisticDto dto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionTopicListForStatisticDto.class);
            if (topicMap.containsKey(e.getId())) {
                List<Long> question = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getQuestionId).distinct().collect(Collectors.toList());
                dto.setQuestionIds(question);
                dto.setQuestionNum(question.size());
            }
            list.add(dto);
        });

        List<Long> chapterIds = herolandTopicQuestions.stream().map(HerolandTopicQuestion::getChapterId).distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(chapterIds)) {
            List<HerolandChapter> chapters = heroLandChapterMapper.getByIds(chapterIds);
            Map<Long, List<HerolandChapter>> idMap = chapters.stream().collect(Collectors.groupingBy(HerolandChapter::getId));
            list.stream().forEach(e -> {
                if (topicMap.containsKey(e.getId())) {
                    List<Long> chapter = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getChapterId).distinct().collect(Collectors.toList());
                    chapter.stream().forEach(i -> {
                        if (idMap.containsKey(i) && ChapterEnum.ZHANG.getType().equals(idMap.get(i).get(0).getContentType())) {
                            e.getChapterList().add(i);
                        } else {
                            e.getSectionList().add(i);
                        }
                    });

                }
            });
        }
        getAdminData(list);
        return list;
    }

    private void getAdminData(List<HeroLandQuestionTopicListForStatisticDto> list) {
        List<String> classKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getClassCode).distinct().collect(Collectors.toList());
        List<String> orgKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getOrgCode).distinct().collect(Collectors.toList());
        List<String> courseKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getCourseCode).distinct().collect(Collectors.toList());
        List<String> gradeKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getGradeCode).distinct().collect(Collectors.toList());
        classKeys.addAll(gradeKeys);
        classKeys.addAll(orgKeys);
        classKeys.addAll(courseKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(classKeys);
        Map<String, List<HerolandBasicDataDP>> keyMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        list.stream().forEach(e -> {
            e.setCourseName(keyMap.containsKey(e.getCourseCode()) ? keyMap.get(e.getCourseCode()).get(0).getDictValue() : "");
            e.setGradeName(keyMap.containsKey(e.getGradeCode()) ? keyMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            e.setClassName(keyMap.containsKey(e.getClassCode()) ? keyMap.get(e.getClassCode()).get(0).getDictValue() : "");
            e.setOrgCode(keyMap.containsKey(e.getOrgCode()) ? keyMap.get(e.getOrgCode()).get(0).getDictValue() : "");
        });
    }

    @Override
    public PageResponse<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForChapterStatistics(HeroLandTopicQuestionForCourseRequest request) {
        PageResponse<HeroLandQuestionTopicListForStatisticDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionTopicListForStatisticDto> list = new ArrayList<>();
        pageResult.setItems(list);
        HeroLandTopicGroupQO qo = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        Page<HeroLandTopicGroup> topicGroupsPageResult = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> heroLandTopicGroupMapper.selectByQuery(qo));
        if (CollectionUtils.isEmpty(topicGroupsPageResult.getResult())) {
            return pageResult;
        }
        List<HeroLandTopicGroup> heroLandTopicGroups = topicGroupsPageResult.getResult();
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(topicIds, null);
        Map<Long, List<HerolandTopicQuestion>> topicMap = herolandTopicQuestions.stream().collect(Collectors.groupingBy(HerolandTopicQuestion::getTopicId));
        heroLandTopicGroups.stream().forEach(e -> {
            HeroLandQuestionTopicListForStatisticDto dto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionTopicListForStatisticDto.class);
            if (topicMap.containsKey(e.getId())) {
                List<Long> questionIds = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getQuestionId).distinct().collect(Collectors.toList());
                Map<Long, List<HerolandQuestionBank>> quesBank = herolandQuestionBankMapper.getByIdsWithDelete(questionIds).stream().collect(Collectors.groupingBy(HerolandQuestionBank::getId));
                dto.setQuestionNum(questionIds.size());
                List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(questionIds, KnowledgeReferEnum.QUESTION.getType());
                Map<Long, List<HerolandKnowledgeRefer>> questionsMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));
                questionIds.stream().forEach(qId -> {
                    HerolandQuestionKnowledgeSimpleDto knowledgeSimpleDto = new HerolandQuestionKnowledgeSimpleDto();
                    knowledgeSimpleDto.setQuestionId(qId);
                    if (questionsMap.containsKey(qId)) {
                        List<HerolandQuestionBank> herolandQuestionBanks = quesBank.get(qId);
                        if (quesBank.containsKey(qId) && !CollectionUtils.isEmpty(herolandQuestionBanks) && herolandQuestionBanks.get(0) != null) {
                            knowledgeSimpleDto.setDiff(herolandQuestionBanks.get(0).getDiff());
                            knowledgeSimpleDto.setType(herolandQuestionBanks.get(0).getType());
                        } else {
                            knowledgeSimpleDto.setDiff(1);
                            knowledgeSimpleDto.setType(1);
                        }
                        List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(questionsMap.get(qId).stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList()));
                        knowledgeSimpleDto.setKnowledge(herolandKnowledges.stream().map(HerolandKnowledge::getKnowledge).collect(Collectors.toList()));
                    }
                });
            }
            list.add(dto);
        });
        getAdminData(list);
        pageResult.setItems(list);
        pageResult.setPageSize(topicGroupsPageResult.getPageSize());
        pageResult.setPage(topicGroupsPageResult.getPageNum());
        pageResult.setTotal((int) topicGroupsPageResult.getTotal());
        return pageResult;
    }

    @Override
    public PageResponse<QuestionTopicDP> getQuestionTopic(HeroLandTopicQuestionForCourseRequest request) {
        PageResponse<QuestionTopicDP> pageResult = new PageResponse<>();
        List<QuestionTopicDP> list = new ArrayList<>();
        pageResult.setItems(list);
        HeroLandTopicGroupQO qo = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        Page<QuestionTopicDP> topicGroupsPageResult = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> {
                    if (request.getType().equals(0)) {
                        herolandQuestionBankMapper.selectQuestionTopicByQuestion(qo);
                    } else {
                        herolandQuestionBankMapper.selectQuestionTopicByTopic(qo);
                    }
                });
        if (CollectionUtils.isEmpty(topicGroupsPageResult.getResult())) {
            return pageResult;
        }
        pageResult.setItems(topicGroupsPageResult.getResult());
        pageResult.setPageSize(topicGroupsPageResult.getPageSize());
        pageResult.setPage(topicGroupsPageResult.getPageNum());
        pageResult.setTotal((int) topicGroupsPageResult.getTotal());
        return pageResult;
    }

    @Override
    public TopicQuestionsForSDto questionsAvailableForS(TopicQuestionsForSRequest request) {
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(request.getTopicId());
        if (!heroLandTopicGroup.getType().equals(TopicTypeConstants.IntegerER_SCHOOL_COMPETITION)) {
            logger.error("this is not a school competition");
            return null;
        }
        if (Objects.equals(request.getToSee(), Boolean.TRUE)){
            return toSee(heroLandTopicGroup, request);
        }else {
            return toCompetiton(heroLandTopicGroup, request);
        }
    }

    /**
     * 比赛时的题目 随机选择
     * @param heroLandTopicGroup
     * @param request
     */
    private TopicQuestionsForSDto toCompetiton(HeroLandTopicGroup heroLandTopicGroup, TopicQuestionsForSRequest request){
        TopicQuestionsForSDto dto = new TopicQuestionsForSDto();
        dto.setTopicId(request.getTopicId());
        dto.setTopicName(heroLandTopicGroup.getTopicName());
        dto.setUserId(request.getUserId());

        PlatformSysUserQO qo = new PlatformSysUserQO();
        qo.setUserId(request.getUserId());
        RpcResult<List<PlatformSysUserDP>> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryUserList(qo);
        if (!platformSysUserDPRpcResult.isSuccess() || CollectionUtils.isEmpty(platformSysUserDPRpcResult.getData())) {
            return null;
        }
        String grade = platformSysUserDPRpcResult.getData().stream().map(PlatformSysUserDP::getGradeCode).findFirst().orElse(null);
        String school = platformSysUserDPRpcResult.getData().stream().map(PlatformSysUserDP::getOrgCode).findFirst().orElse(null);
        //如果为空则默认查所有的科目
        if (StringUtils.isEmpty(request.getCourseCode())) {
            return null;
        }
        Long lastId = getLastId();
        List<HerolandQuestionBank> availQues = Lists.newArrayList();
        List<HerolandQuestionBank> bankList = herolandQuestionBankMapper.selectQuestionsByGradeAndCoursesForS(grade, request.getCourseCode(), BankTypeEnum.INTERSCHOOL.getLevel(), lastId, 12);
        //如果查出的是少于12题，则从头继续轮询
        availQues.addAll(bankList);
        if (bankList.size() < 12){
            Integer count = 12 - bankList.size();
            List<HerolandQuestionBank> bankListAppend = herolandQuestionBankMapper.selectQuestionsByGradeAndCoursesForS(grade, request.getCourseCode(), BankTypeEnum.INTERSCHOOL.getLevel(), null, count);
            availQues.addAll(bankListAppend);
            //去重，已重写hashCode和equals
            availQues = availQues.stream().distinct().collect(Collectors.toList());
        }

        List<Long> qbIds = availQues.stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(qbIds)){
            return dto;
        }
        List<HerolandQuestionBankDetail> bankDetails = herolandQuestionBankDetailMapper.getByQtId(qbIds);
        Map<Long, List<HerolandQuestionBankDetail>> bankDetailsMap = bankDetails.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));

        availQues.stream().forEach(e -> {
            HeroLandQuestionBankSimpleDto simpleDto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionBankSimpleDto.class);
            dto.getQuestions().add(simpleDto);
            if (bankDetailsMap.containsKey(e.getId())){
                HerolandQuestionBankDetail detail = bankDetailsMap.get(e.getId()).get(0);
                simpleDto.setOptionAnswer(detail.getOptionAnswer());
                simpleDto.setParse(detail.getParse());
                simpleDto.setStormAnswer(detail.getStormAnswer());
                simpleDto.setOptions(JSON.parseArray(detail.getOption(), QuestionOptionDto.class));
                simpleDto.setAnalysis(detail.getAnalysis());
            }
        });
        return dto;
    }

    /**
     * 比赛完成后查看题目
     * @param heroLandTopicGroup
     * @param request
     */
    private TopicQuestionsForSDto toSee(HeroLandTopicGroup heroLandTopicGroup, TopicQuestionsForSRequest request){
        TopicQuestionsForSDto dto = new TopicQuestionsForSDto();
        dto.setTopicId(request.getTopicId());
        dto.setTopicName(heroLandTopicGroup.getTopicName());
        dto.setUserId(request.getUserId());

        PlatformSysUserQO qo = new PlatformSysUserQO();
        qo.setUserId(request.getUserId());
        RpcResult<List<PlatformSysUserDP>> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryUserList(qo);
        if (!platformSysUserDPRpcResult.isSuccess() || CollectionUtils.isEmpty(platformSysUserDPRpcResult.getData())) {
            return null;
        }
        HeroLandQuestionRecordDetailExample example = new HeroLandQuestionRecordDetailExample();
        HeroLandQuestionRecordDetailExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdIn(Lists.newArrayList(heroLandTopicGroup.getId()+""));
        criteria.andUserIdEqualTo(qo.getUserId());
        if (StringUtils.isNotBlank(request.getCourseCode())){
            criteria.andSubjectCodeEqualTo(request.getCourseCode());
        }
        List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = heroLandQuestionRecordDetailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(heroLandQuestionRecordDetails)){
            return dto;
        }
        List<Long> qbIds = heroLandQuestionRecordDetails.stream().map(HeroLandQuestionRecordDetail::getQuestionId).distinct().collect(Collectors.toList());
        List<HerolandQuestionBank> banks = herolandQuestionBankMapper.getByIdsWithDelete(qbIds);
        List<HerolandQuestionBankDetail> bankDetails = herolandQuestionBankDetailMapper.getByQtId(qbIds);
        Map<Long, List<HerolandQuestionBankDetail>> bankDetailsMap = bankDetails.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));

        banks.stream().forEach(e -> {
            HeroLandQuestionBankSimpleDto simpleDto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionBankSimpleDto.class);
            dto.getQuestions().add(simpleDto);
            if (bankDetailsMap.containsKey(e.getId())){
                HerolandQuestionBankDetail detail = bankDetailsMap.get(e.getId()).get(0);
                simpleDto.setOptionAnswer(detail.getOptionAnswer());
                simpleDto.setParse(detail.getParse());
                simpleDto.setStormAnswer(detail.getStormAnswer());
                simpleDto.setOptions(JSON.parseArray(detail.getOption(), QuestionOptionDto.class));
                simpleDto.setAnalysis(detail.getAnalysis());
            }
        });
        return dto;
    }


    private List<HerolandCourse> getPartInfoForSchoolCompetitionTopic(String userId, Long topicId){
        List<HerolandCourse> courseList = Lists.newArrayList();
        PlatformSysUserQO qo = new PlatformSysUserQO();
        qo.setUserId(userId);
        RpcResult<List<PlatformSysUserDP>> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryUserList(qo);
        if (!platformSysUserDPRpcResult.isSuccess() || CollectionUtils.isEmpty(platformSysUserDPRpcResult.getData())) {
            return null;
        }
        String grade = platformSysUserDPRpcResult.getData().stream().map(PlatformSysUserDP::getGradeCode).findFirst().orElse(null);
        String school = platformSysUserDPRpcResult.getData().stream().map(PlatformSysUserDP::getOrgCode).findFirst().orElse(null);

        if (StringUtils.isBlank(school) || StringUtils.isBlank(grade)) {
            logger.error("query error department");
            return courseList;
        }
        HerolandTopicForSQO sqo = new HerolandTopicForSQO();
        sqo.setTopicIds(Lists.newArrayList(topicId));
        sqo.setOrgCode(school);
        sqo.setGradeCode(grade);
        List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
        if (CollectionUtils.isEmpty(herolandTopicGroupPartDPS)) {
            return courseList;
        }
        //根据grade和org去查所有的科目
        List<HerolandSchoolCourse> schoolCourses = herolandSchoolCourseMapper.getBySchoolListAndCourse(Lists.newArrayList(school), null);
        if (CollectionUtils.isEmpty(schoolCourses)) {
            return courseList;
        }
        List<Long> courseIds = schoolCourses.stream().map(HerolandSchoolCourse::getCourseId).collect(Collectors.toList());
        courseList = herolandCourseMapper.selectByPrimaryKeys(courseIds);
        courseList = courseList.stream().filter(e -> grade.equalsIgnoreCase(e.getGrade())).collect(Collectors.toList());
        return courseList;
    }


    private Long getLastId(){
        Long maxId = herolandQuestionBankMapper.maxId(null, null, BankTypeEnum.INTERSCHOOL.getLevel());
        // lastId
        //通过随机一个数
        Long lastId = NumberUtils.nextLong(random, maxId);
        return lastId;
    }

    @Override
    public List<HerolandCourseSimpleDto> courseAvailableForS(TopicSCourseForSRequest request) {
        List<HerolandCourseSimpleDto> result = new ArrayList<>();
        List<HerolandCourse> herolandCourses = getPartInfoForSchoolCompetitionTopic(request.getUserId(), request.getTopicId());
        if (!CollectionUtils.isEmpty(herolandCourses)){
            List<String> courseCodeList = herolandCourses.stream().map(HerolandCourse::getCourse).distinct().collect(Collectors.toList());
            List<HerolandBasicDataDP> courseDataList = heroLandAdminService.getDictInfoByKeys(courseCodeList);
            Map<String, HerolandBasicDataDP> courseDataMap = courseDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity()));
            result = courseCodeList.stream().map(e -> {
                HerolandCourseSimpleDto simpleDto = new HerolandCourseSimpleDto();
                simpleDto.setCourse(e);
                simpleDto.setCourseName(courseDataMap.containsKey(e) ? courseDataMap.get(e).getDictValue() : "");
                HeroLandCompetitionRecordQO request1 = new HeroLandCompetitionRecordQO();
                request1.setTopicId(request.getTopicId()+"");
                request1.setUserId(request.getUserId());
                request1.setSubjectCode(simpleDto.getCourse());
                ResponseBody<List<HeroLandCompetitionRecordDP>> competitionRecords = heroLandCompetitionRecordService.getCompetitionRecords(request1);
                if (competitionRecords.isSuccess() && !CollectionUtils.isEmpty(competitionRecords.getData())){
                    simpleDto.setHasFinished(true);
                }else {
                    simpleDto.setHasFinished(false);
                }
                return simpleDto;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<HeroLandQuestionRecordDetailDP> judgeQuestionResult(List<HeroLandQuestionRecordDetailDP> questions) {
        logger.info("question:{}",JSON.toJSONString(questions));
        List<HerolandQuestionBankDetail> questionBanks = herolandQuestionBankDetailMapper.getByQtId(questions.parallelStream().map(HeroLandQuestionRecordDetailDP::getId).collect(Collectors.toList()));
        logger.info("查出questions,{}",JSON.toJSONString(questionBanks));
        if (!CollectionUtils.isEmpty(questionBanks)) {
            questionBanks.forEach(v->{
                questions.forEach(q->{
                    if (v.getQbId().equals(q.getId())){
                        q.setCorrectAnswer(q.getYourAnswer().equalsIgnoreCase(v.getOptionAnswer()));
                    }
                });
            });
        }
        return questions;
    }

    @Override
    public HeroLandTopicForWDto topicWForStudent(TopicWForStudentRequest request) {
        Date now = new Date();
        PlatformSysUserQO qo = new PlatformSysUserQO();
        qo.setUserId(request.getUserId());
        RpcResult<List<PlatformSysUserDP>> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryUserList(qo);
        if (!platformSysUserDPRpcResult.isSuccess() || CollectionUtils.isEmpty(platformSysUserDPRpcResult.getData())) {
            return null;
        }
        String grade = platformSysUserDPRpcResult.getData().stream().map(PlatformSysUserDP::getGradeCode).findFirst().orElse(null);

        HeroLandTopicGroupExample heroLandTopicGroupExample = new HeroLandTopicGroupExample();
        HeroLandTopicGroupExample.Criteria criteria = heroLandTopicGroupExample.createCriteria();
        if (Objects.equals(request.getAction(), "REGISTER")){
            criteria.andRegisterBeginTimeLessThanOrEqualTo(now).andRegisterEndTimeGreaterThanOrEqualTo(now).andTypeEqualTo(TopicTypeConstants.WORLD_COMPETITION).andIsDeletedEqualTo(false);
            List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(heroLandTopicGroupExample);
            if (!CollectionUtils.isEmpty(heroLandTopicGroups)){
                heroLandTopicGroups = heroLandTopicGroups.stream().filter(e -> e.getRegisterCount() == null || e.getCountLimit() == null || e.getRegisterCount() < e.getCountLimit())
                        .sorted(Comparator.comparing(HeroLandTopicGroup::getRegisterBeginTime))
                        .collect(Collectors.toList());
                Map<Long, HeroLandTopicGroup> topicGroupMap = heroLandTopicGroups.stream().collect(Collectors.toMap(HeroLandTopicGroup::getId, Function.identity()));
                List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
                HerolandTopicForSQO sqo = new HerolandTopicForSQO();
                sqo.setTopicIds(topicIds);
                sqo.setGradeCode(grade);
                List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
                List<Long> partTopicIds = herolandTopicGroupPartDPS.stream().map(HerolandTopicGroupPartDP::getTopicId).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(partTopicIds)){
                    return null;
                }
                HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
                example.createCriteria().andTopicIdIn(partTopicIds).andJoinUserEqualTo(request.getUserId()).andStateEqualTo(TopicJoinConstant.JOIND).andIsDeletedEqualTo(false);
                List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                        herolandTopicJoinUserMapper.selectByExample(example);
               //如果有未报名的可以去报名，有一个了就不推荐报名了
                if (CollectionUtils.isEmpty(herolandTopicJoinUsers) && !CollectionUtils.isEmpty(partTopicIds)){
                    HeroLandTopicGroup heroLandTopicGroup = topicGroupMap.get(partTopicIds.get(0));
                    HeroLandTopicForWDto heroLandTopicForWDto = BeanCopyUtils.copyByJSON(heroLandTopicGroup, HeroLandTopicForWDto.class);
                    heroLandTopicForWDto.setStudentJoinState(TopicJoinConstant.NONJOINED);
                    heroLandTopicForWDto.setTopicId(heroLandTopicGroup.getId());
                    heroLandTopicForWDto.setStudentFinishState(TopicJoinConstant.UNFINISHED);
                    return heroLandTopicForWDto;
                }
            }
        }else if(Objects.equals(request.getAction(), "BATTLE")){
            criteria.andStartTimeLessThanOrEqualTo(now).andTypeEqualTo(TopicTypeConstants.WORLD_COMPETITION).andIsDeletedEqualTo(false);
            List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(heroLandTopicGroupExample);
            if (!CollectionUtils.isEmpty(heroLandTopicGroups)){
                heroLandTopicGroups = heroLandTopicGroups.stream().filter(e -> (e.getStartTime().getTime() - now.getTime()) < 10 * 60 *1000)
                        .sorted(Comparator.comparing(HeroLandTopicGroup::getStartTime))
                        .collect(Collectors.toList());
                Map<Long, HeroLandTopicGroup> topicGroupMap = heroLandTopicGroups.stream().collect(Collectors.toMap(HeroLandTopicGroup::getId, Function.identity()));
                List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
                HerolandTopicForSQO sqo = new HerolandTopicForSQO();
                sqo.setTopicIds(topicIds);
                sqo.setGradeCode(grade);
                if (StringUtils.isNotBlank(request.getCourseCode())){
                    sqo.setCourseCodes(Lists.newArrayList(request.getCourseCode()));
                }
                List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
                List<Long> partTopicIds = herolandTopicGroupPartDPS.stream().map(HerolandTopicGroupPartDP::getTopicId).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(partTopicIds)){
                    return null;
                }
                HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
                example.createCriteria().andTopicIdIn(partTopicIds).andJoinUserEqualTo(request.getUserId()).andStateEqualTo(TopicJoinConstant.JOIND).andIsDeletedEqualTo(false);
                List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                        herolandTopicJoinUserMapper.selectByExample(example);
                //如果有报名的，弹出最近的
                if (!CollectionUtils.isEmpty(herolandTopicJoinUsers)){
                    HeroLandTopicGroup heroLandTopicGroup = topicGroupMap.get(herolandTopicJoinUsers.get(0).getTopicId());
                    if (heroLandTopicGroup != null){
                        HeroLandTopicForWDto heroLandTopicForWDto = BeanCopyUtils.copyByJSON(heroLandTopicGroup, HeroLandTopicForWDto.class);
                        heroLandTopicForWDto.setStudentJoinState(TopicJoinConstant.JOIND);
                        heroLandTopicForWDto.setTopicId(heroLandTopicGroup.getId());
                        return heroLandTopicForWDto;
                    }
                }
            }
        }
        return null;
    }


    private HeroLandTopicForWDto process(List<HeroLandTopicGroup> herolandTopics, String userId){
        HeroLandTopicForWDto forWDto = null;
        for (HeroLandTopicGroup heroLandTopicGroup : herolandTopics){
            HeroLandTopicForWDto heroLandTopicForWDto = BeanCopyUtils.copyByJSON(heroLandTopicGroup, HeroLandTopicForWDto.class);
            heroLandTopicForWDto.setStudentJoinState(TopicJoinConstant.JOIND);
            heroLandTopicForWDto.setTopicId(heroLandTopicGroup.getId());
            heroLandTopicForWDto.setState(getTopicState(heroLandTopicGroup));
            HeroLandQuestionRecordDetailExample example1 = new HeroLandQuestionRecordDetailExample();
            HeroLandQuestionRecordDetailExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andTopicIdEqualTo(heroLandTopicGroup.getId()+"");
            criteria1.andUserIdEqualTo(userId);
            List<HeroLandQuestionRecordDetail> heroLandQuestionRecordDetails = heroLandQuestionRecordDetailMapper.selectByExample(example1);
            if (CollectionUtils.isEmpty(heroLandQuestionRecordDetails)){
                forWDto = heroLandTopicForWDto;
                return forWDto;
            }
            List<Long> hasFinished = heroLandQuestionRecordDetails.stream().map(HeroLandQuestionRecordDetail::getQuestionId).distinct().collect(Collectors.toList());
            List<HerolandTopicQuestion> questions = herolandTopicQuestionMapper.selectByTopics(Lists.newArrayList(heroLandTopicGroup.getId()), null);
            if (CollectionUtils.isEmpty(questions)){
                continue;
            }
            if (hasFinished.size() < questions.size()){
                heroLandTopicForWDto.setStudentFinishState(TopicJoinConstant.UNFINISHED);
                forWDto = heroLandTopicForWDto;
                return forWDto;
            }
        }
        return forWDto;
    }

    @Override
    public HeroLandTopicForWDto topicWForStudentLastJoined(TopicWForStudentJoinedRequest request) {
        if (!Objects.equals(request.getTopicType(), TopicTypeConstants.WORLD_COMPETITION) && !Objects.equals(request.getTopicType(), TopicTypeConstants.IntegerER_SCHOOL_COMPETITION)){
            return null;
        }
        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
        HerolandTopicJoinUserExample.Criteria criteria = example.createCriteria();
        criteria.andJoinUserEqualTo(request.getUserId()).andTopicTypeEqualTo(request.getTopicType()).andStateEqualTo(TopicJoinConstant.JOIND).andIsDeletedEqualTo(false);
        List<HerolandTopicJoinUser> list = herolandTopicJoinUserMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)){
            Date now = new Date();
            List<HerolandTopicJoinUser> sort = list.stream().filter(e -> Objects.nonNull(e.getRegisterTime())).sorted(Comparator.comparing(HerolandTopicJoinUser::getRegisterTime).reversed()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sort)){
                List<Long> topicIds = sort.stream().map(HerolandTopicJoinUser::getTopicId).distinct().collect(Collectors.toList());
                HeroLandTopicGroupExample groupExample = new HeroLandTopicGroupExample();
                HeroLandTopicGroupExample.Criteria criteria1 = groupExample.createCriteria();
                criteria1.andIsDeletedEqualTo(false).andEndTimeGreaterThan(now).andIdIn(topicIds);
                groupExample.setOrderByClause("end_time asc");
                List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByExample(groupExample);
                if (CollectionUtils.isEmpty(heroLandTopicGroups)){
                    return null;
                }
                if (Objects.equals(request.getTopicType(), TopicTypeConstants.WORLD_COMPETITION)){
                    return process(heroLandTopicGroups, request.getUserId());
                }else {
                    HeroLandTopicForWDto heroLandTopicForWDto = BeanCopyUtils.copyByJSON(heroLandTopicGroups.get(0), HeroLandTopicForWDto.class);
                    heroLandTopicForWDto.setStudentJoinState(TopicJoinConstant.JOIND);
                    heroLandTopicForWDto.setTopicId(heroLandTopicGroups.get(0).getId());
                    heroLandTopicForWDto.setState(getTopicState(heroLandTopicGroups.get(0)));
                }
            }
        }
        return null;
    }

    private String getTopicState(HeroLandTopicGroup heroLandTopicGroup) {
        String topicState = "";
        Date now = new Date();
        if (now.before(heroLandTopicGroup.getStartTime())){
            topicState = TopicJoinConstant.NOTSTART;
        }else if (now.after(heroLandTopicGroup.getEndTime())){
            topicState = TopicJoinConstant.OVERDUE;
        }else {
            topicState = TopicJoinConstant.DOING;
        }
        return topicState;
    }

}
