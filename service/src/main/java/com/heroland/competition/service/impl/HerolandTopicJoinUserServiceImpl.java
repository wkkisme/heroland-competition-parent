package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.constants.TopicTypeConstants;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupMapper;
import com.heroland.competition.dal.mapper.HerolandTopicJoinUserMapper;
import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUser;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUserExample;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;
import com.heroland.competition.domain.qo.HerolandTopicForSQO;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import com.heroland.competition.service.HerolandTopicJoinUserService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.platform.sso.domain.dp.PlatformSysUserClassDP;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Component
@Slf4j
public class HerolandTopicJoinUserServiceImpl implements HerolandTopicJoinUserService {

    @Resource
    private HerolandTopicJoinUserMapper herolandTopicJoinUserMapper;

    @Resource
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Resource
    private HerolandTopicGroupPartService herolandTopicGroupPartService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private HerolandDiamondService herolandDiamondService;

    @Resource
    private HeroLandAdminService heroLandAdminService;


    @Override
    public Boolean addJoin(HerolandTopicJoinUserDP dp) {
        HerolandTopicJoinUserDP herolandTopicJoinUserDP = dp.checkAndBuildBefore();
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(dp.getTopicId());
        if(heroLandTopicGroup == null){
            throw new AppSystemException(HerolandErrMsgEnum.ERROR_QUERY_PARAM.getErrorMessage());
        }
        if (Objects.equals(heroLandTopicGroup.getType(), TopicTypeConstants.WORLD_COMPETITION)){
            if (!(heroLandTopicGroup.getRegisterCount() == null || heroLandTopicGroup.getRegisterCount() < heroLandTopicGroup.getCountLimit())){
                throw new AppSystemException(HerolandErrMsgEnum.JOINED_LIMIT.getErrorMessage());
            }
        }

        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
        example.createCriteria().andTopicIdEqualTo(dp.getTopicId()).andJoinUserIn(dp.getJoinUsers()).andStateEqualTo(dp.getState());
        List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                herolandTopicJoinUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(herolandTopicJoinUsers)){
            List<String> hasJoined = herolandTopicJoinUsers.stream().map(HerolandTopicJoinUser::getJoinUser).distinct().collect(Collectors.toList());
            PlatformSysUserQO qo = new PlatformSysUserQO();
            qo.setUserIds(hasJoined);
            RpcResult<List<PlatformSysUserDP>> listRpcResult = platformSsoUserServiceFacade.queryUserList(qo);
            if (listRpcResult.isSuccess() && !CollectionUtils.isEmpty(listRpcResult.getData())){
                List<String> hasJoinedName = listRpcResult.getData().stream().filter(e -> !StringUtils.isEmpty(e.getUserName())).map(PlatformSysUserDP::getUserName).collect(Collectors.toList());
                String userName = JSON.toJSONString(hasJoinedName);
                throw new AppSystemException(userName + HerolandErrMsgEnum.HAS_JOINED.getErrorMessage());
            }
        }
        List<HerolandTopicJoinUser> list = Lists.newArrayList();
        dp.getJoinUsers().stream().forEach(e -> {
            HerolandTopicJoinUser herolandTopicJoinUser = BeanCopyUtils.copyByJSON(herolandTopicJoinUserDP, HerolandTopicJoinUser.class);
            herolandTopicJoinUser.setTopicType(heroLandTopicGroup.getType());
            herolandTopicJoinUser.setRegisterTime(new Date());
            herolandTopicJoinUser.setJoinUser(e);
            list.add(herolandTopicJoinUser);
        });

        //世界赛需要扣除钻石
        if (Objects.equals(heroLandTopicGroup.getType(), TopicTypeConstants.WORLD_COMPETITION)){
            HeroLandAccountManageQO qo = new HeroLandAccountManageQO();
            qo.setCompetitionType(CompetitionEnum.WORLD);
            qo.setUserId(dp.getJoinUsers().get(0));
            qo.setRemark("报名世界赛:"+ dp.getTopicId());
            qo.setNum(1);
            heroLandAccountService.decrUserDiamond(qo);

            HerolandDiamRequest herolandDiamRequest = new HerolandDiamRequest();
            herolandDiamRequest.setUserId(dp.getJoinUsers().get(0));
            herolandDiamRequest.setBizGroup(CompetitionEnum.WORLD.getType().toString());
            herolandDiamRequest.setBizName(CompetitionEnum.WORLD.getName());
            herolandDiamRequest.setNum(1);
            herolandDiamRequest.setChangeStockType(2);
            herolandDiamondService.createDiamondRecord(herolandDiamRequest);

            //对报名人数+1
            heroLandTopicGroupMapper.incrRegisterCount(dp.getTopicId(), heroLandTopicGroup.getRegisterCount() + 1, heroLandTopicGroup.getRegisterCount());

        }

        return herolandTopicJoinUserMapper.batchInsert(list) > 0;
    }

    @Override
    public Boolean cancel(HerolandTopicJoinUserDP dp) {
//        HerolandTopicJoinUserDP herolandTopicJoinUserDP = dp.checkAndBuildBefore();
//        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
//        example.createCriteria().andTopicIdEqualTo(dp.getTopicId()).andJoinUserEqualTo(dp.getJoinUser()).andStateEqualTo(TopicJoinConstant.JOIND);
//        List<HerolandTopicJoinUser> herolandTopicJoinUsers =
//                herolandTopicJoinUserMapper.selectByExample(example);
//        if(CollectionUtils.isEmpty(herolandTopicJoinUsers)){
//            throw new AppSystemException(HerolandErrMsgEnum.NOT_JOINED.getErrorMessage());
//        }
//        HerolandTopicJoinUser herolandTopicJoinUser = BeanCopyUtils.copyByJSON(herolandTopicJoinUserDP, HerolandTopicJoinUser.class);
//        return herolandTopicJoinUserMapper.updateByPrimaryKeySelective(herolandTopicJoinUser) > 0;
        return false;
    }

    @Override
    public HerolandTopicJoinStatisticsDto statistics(HerolandTopicJoinUserDP dp) {
        HerolandTopicJoinStatisticsDto dto = new HerolandTopicJoinStatisticsDto();
        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
        example.createCriteria().andTopicIdEqualTo(dp.getTopicId()).andStateEqualTo(dp.getState());
        List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                herolandTopicJoinUserMapper.selectByExample(example);
        List<String> users = herolandTopicJoinUsers.stream().map(HerolandTopicJoinUser::getJoinUser).distinct().collect(Collectors.toList());
        dto.setState(dp.getState());
        dto.setTopicId(dp.getTopicId());
        dto.setTotalUserCount(users.size());
        return dto;
    }

    @Override
    public PageResponse<HeroLandTopicForSDto> canOperableTopics(HerolandTopicCanSeeQO qo) {

        PlatformSysUserQO userQO = new PlatformSysUserQO();
        userQO.setUserId(qo.getUserId());
        RpcResult<List<PlatformSysUserDP>> platformSysUserDPRpcResult = platformSsoUserServiceFacade.queryUserList(userQO);
        if (!platformSysUserDPRpcResult.isSuccess() || CollectionUtils.isEmpty(platformSysUserDPRpcResult.getData())) {
            return null;
        }
        //只要不是学生可以看到所有的赛事，是学生只能看到当前学校的赛事，对于可看的暂时不去限制太死
//        if (StringUtils.isEmpty(qo.getActionType())){
//            qo.setActionType(TopicJoinConstant.CAN_SEE);
//        }
//        if (qo.getActionType().equalsIgnoreCase(TopicJoinConstant.CAN_SEE)){
//
//        }
        return canSeeTopics(qo, platformSysUserDPRpcResult.getData());
    }


    private PageResponse<HeroLandTopicForSDto> canSeeTopics(HerolandTopicCanSeeQO qo, List<PlatformSysUserDP> userDPS){
        PageResponse<HeroLandTopicForSDto> pageResult = new PageResponse<>();
        List<HeroLandTopicForSDto> list = new ArrayList<>();
        pageResult.setItems(list);
        //只要不是学生可以看到所有的赛事，是学生只能看到当前学校的赛事，对于可看的暂时不去限制太死
        Page<HeroLandTopicGroup> dataPage = null;
        if (!Objects.equals(userDPS.get(0).getType(), 0) ){
            dataPage = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                    () -> heroLandTopicGroupMapper.selectByTypeAndState(qo.getTopicType(), qo.getTopicState()));

        }else {
            //如果是世界赛，只需要看到年级，不需要学校
            String gradeCode = userDPS.stream().map(PlatformSysUserDP::getGradeCode).findFirst().orElse(null);
            String orgCode = userDPS.stream().map(PlatformSysUserDP::getOrgCode).findFirst().orElse(null);

            if (Objects.equals(TopicTypeConstants.WORLD_COMPETITION, qo.getTopicType())){
                dataPage = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                        () -> heroLandTopicGroupMapper.selectByTypeAndStateAndPart(qo.getTopicType(), qo.getTopicState(), null, gradeCode));
            }else {
                dataPage = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                        () -> heroLandTopicGroupMapper.selectByTypeAndStateAndPart(qo.getTopicType(), qo.getTopicState(), orgCode, gradeCode));
            }
        }

        if (dataPage == null || CollectionUtils.isEmpty(dataPage.getResult())){
            return pageResult;
        }
        List<Long> topicIds = dataPage.getResult().stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        HerolandTopicForSQO sqo = new HerolandTopicForSQO();
        sqo.setTopicIds(topicIds);
        List<HerolandTopicGroupPartDP> partDPS = herolandTopicGroupPartService.listPartByTopicIds(sqo);
        List<String> dataKeys = Lists.newArrayList();

        //查看学生的参与信息
        List<HerolandTopicJoinUser> joinUsers = Lists.newArrayList();
        if (Objects.equals(userDPS.get(0).getType(), 0)){
            HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
            HerolandTopicJoinUserExample.Criteria criteria = example.createCriteria();
            criteria.andTopicIdIn(topicIds).andJoinUserEqualTo(qo.getUserId()).andStateEqualTo("JOINED");
            joinUsers = herolandTopicJoinUserMapper.selectByExample(example);
        }
        Map<Long, List<HerolandTopicJoinUser>> joinMap = joinUsers.stream().collect(Collectors.groupingBy(HerolandTopicJoinUser::getTopicId));

        //世界赛
        if (Objects.equals(TopicTypeConstants.WORLD_COMPETITION, qo.getTopicType())){
            partDPS.stream().forEach(e -> {
                dataKeys.add(e.getGradeCode());
                dataKeys.add(e.getCourseCode());
            });
        }else {
            partDPS.stream().forEach(e -> {
                dataKeys.add(e.getGradeCode());
                dataKeys.add(e.getOrgCode());
            });
        }
        List<String> keys = dataKeys.stream().distinct().collect(Collectors.toList());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, HerolandBasicDataDP> dataMap = dictInfoByKeys.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity()));
        Map<Long, List<HerolandTopicGroupPartDP>> partMap = partDPS.stream().collect(Collectors.groupingBy(HerolandTopicGroupPartDP::getTopicId));
        list = dataPage.getResult().stream().map(e ->convertToTopicForSDto(e, partMap, dataMap, joinMap)).collect(Collectors.toList());
        pageResult.setItems(list);
        pageResult.setPageSize(dataPage.getPageSize());
        pageResult.setPage(dataPage.getPageNum());
        pageResult.setTotal((int) dataPage.getTotal());
        return pageResult;

    }

    private HeroLandTopicForSDto convertToTopicForSDto(HeroLandTopicGroup topicGroup,Map<Long, List<HerolandTopicGroupPartDP>> partMap, Map<String, HerolandBasicDataDP> dataMap , Map<Long, List<HerolandTopicJoinUser>> joinMap){
        Date now = new Date();
        HeroLandTopicForSDto heroLandTopicForSDto = new HeroLandTopicForSDto();
        heroLandTopicForSDto.setTopicName(topicGroup.getTopicName());
        heroLandTopicForSDto.setTopicId(topicGroup.getId());
        heroLandTopicForSDto.setStartTime(topicGroup.getStartTime());
        heroLandTopicForSDto.setEndTime(topicGroup.getEndTime());
        heroLandTopicForSDto.setDesc(topicGroup.getDescription());
        heroLandTopicForSDto.setRegisterbeginTime(topicGroup.getRegisterBeginTime());
        heroLandTopicForSDto.setRegisterEndTime(topicGroup.getRegisterEndTime());
        heroLandTopicForSDto.setRegisterCount(topicGroup.getRegisterCount());
        heroLandTopicForSDto.setCountLimit(topicGroup.getCountLimit());
        if (joinMap.containsKey(topicGroup.getId())){
            heroLandTopicForSDto.setStudentJoinState("JOINED");
        }
        if (now.before(heroLandTopicForSDto.getStartTime())){
            heroLandTopicForSDto.setState(TopicJoinConstant.NOTSTART);
        }else if(now.after(heroLandTopicForSDto.getEndTime())){
            heroLandTopicForSDto.setState(TopicJoinConstant.OVERDUE);
        }else {
            heroLandTopicForSDto.setState(TopicJoinConstant.DOING);
        }
        if (partMap.containsKey(topicGroup.getId())){
            String gradeCode = partMap.get(topicGroup.getId()).get(0).getGradeCode();
            if (dataMap.containsKey(gradeCode)){
                heroLandTopicForSDto.setGradeName(dataMap.get(gradeCode).getDictValue());
                heroLandTopicForSDto.setGradeCode(gradeCode);
            }

            if (Objects.equals(TopicTypeConstants.WORLD_COMPETITION, topicGroup.getType())){
                List<HerolandSchoolDto> courses = Lists.newArrayList();
                partMap.get(topicGroup.getId()).stream().forEach(e -> {
                    if (dataMap.containsKey(e.getCourseCode())){
                        heroLandTopicForSDto.getCourseNameList().add(dataMap.get(e.getCourseCode()).getDictValue());
                        HerolandSchoolDto dto = new HerolandSchoolDto();
                        dto.setKey(e.getCourseCode());
                        dto.setName(dataMap.get(e.getCourseCode()).getDictValue());
                        courses.add(dto);
                    }
                });
                heroLandTopicForSDto.setWorldCourseDtos(courses);
            }else {
                List<HerolandSchoolDto> schools = Lists.newArrayList();
                partMap.get(topicGroup.getId()).stream().forEach(e -> {
                    if (dataMap.containsKey(e.getOrgCode())){
                        HerolandSchoolDto dto = new HerolandSchoolDto();
                        dto.setKey(e.getOrgCode());
                        dto.setName(dataMap.get(e.getOrgCode()).getDictValue());
                        schools.add(dto);
                    }
                });
                heroLandTopicForSDto.setSchoolDtos(schools);
            }
        }
        return heroLandTopicForSDto;

    }



}
