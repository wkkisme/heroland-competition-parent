package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupMapper;
import com.heroland.competition.dal.mapper.HerolandTopicJoinUserMapper;
import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUser;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUserExample;
import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HerolandTopicJoinUserService;
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
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    @Override
    public Boolean addJoin(HerolandTopicJoinUserDP dp) {
        HerolandTopicJoinUserDP herolandTopicJoinUserDP = dp.checkAndBuildBefore();
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
            herolandTopicJoinUser.setJoinUser(e);
            list.add(herolandTopicJoinUser);
        });
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
        PageResponse<HeroLandTopicForSDto> pageResult = new PageResponse<>();

        PlatformSysUserClassQO userQo = new PlatformSysUserClassQO();
        qo.setUserId(qo.getUserId());
        ResponseBody<List<PlatformSysUserClassDP>> listResponseBody = platformSsoUserClassServiceFacade.queryUserClassList(userQo);
        if (!listResponseBody.isSuccess() || CollectionUtils.isEmpty(listResponseBody.getData())){
            return null;
        }
        List<PlatformSysUserClassDP> data = listResponseBody.getData();
        //只要不是学生可以看到所有的赛事，是学生只能看到当前学校的赛事，对于可看的暂时不去限制太死
//        if (StringUtils.isEmpty(qo.getActionType())){
//            qo.setActionType(TopicJoinConstant.CAN_SEE);
//        }
//        if (qo.getActionType().equalsIgnoreCase(TopicJoinConstant.CAN_SEE)){
//
//        }
        return canSeeTopics(qo, data);
    }


    private PageResponse<HeroLandTopicForSDto> canSeeTopics(HerolandTopicCanSeeQO qo, List<PlatformSysUserClassDP> data){
        PageResponse<HeroLandTopicForSDto> pageResult = new PageResponse<>();
        List<HeroLandTopicForSDto> list = new ArrayList<>();
        pageResult.setItems(list);
        //只要不是学生可以看到所有的赛事，是学生只能看到当前学校的赛事，对于可看的暂时不去限制太死
        Page<HeroLandTopicGroup> dataPage = null;
        if (!Objects.equals(data.get(0).getUserType(), 0) ){
            dataPage = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                    () -> heroLandTopicGroupMapper.selectByTypeAndState(qo.getTopicType(), qo.getTopicState()));

        }else {

            dataPage = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                    () -> heroLandTopicGroupMapper.selectByTypeAndStateAndPart(qo.getTopicType(), qo.getTopicState(), data.get(0).getOrgCode()));
        }

        if (dataPage == null || CollectionUtils.isEmpty(dataPage.getResult())){
            return pageResult;
        }

        list = dataPage.getResult().stream().map(this::convertToTopicForSDto).collect(Collectors.toList());
        pageResult.setItems(list);
        pageResult.setPageSize(dataPage.getPageSize());
        pageResult.setPage(dataPage.getPageNum());
        pageResult.setTotal((int) dataPage.getTotal());
        return pageResult;

    }

    private HeroLandTopicForSDto convertToTopicForSDto(HeroLandTopicGroup topicGroup){
        Date now = new Date();
        HeroLandTopicForSDto heroLandTopicForSDto = new HeroLandTopicForSDto();
        heroLandTopicForSDto.setTopicName(topicGroup.getTopicName());
        heroLandTopicForSDto.setTopicId(topicGroup.getId());
        heroLandTopicForSDto.setStartTime(topicGroup.getStartTime());
        heroLandTopicForSDto.setEndTime(topicGroup.getEndTime());
        heroLandTopicForSDto.setDesc(topicGroup.getDescription());
        if (now.before(heroLandTopicForSDto.getStartTime())){
            heroLandTopicForSDto.setState(TopicJoinConstant.NOTSTART);
        }else if(now.after(heroLandTopicForSDto.getEndTime())){
            heroLandTopicForSDto.setState(TopicJoinConstant.OVERDUE);
        }else {
            heroLandTopicForSDto.setState(TopicJoinConstant.DOING);
        }
        return heroLandTopicForSDto;

    }



}
