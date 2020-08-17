package com.heroland.competition.service.impl;

import com.anycommon.response.expception.AppSystemException;
import com.heroland.competition.common.constant.TopicJoinConstant;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HerolandTopicJoinUserMapper;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUser;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUserExample;
import com.heroland.competition.domain.dp.HerolandTopicJoinUserDP;
import com.heroland.competition.domain.dto.HerolandTopicJoinStatisticsDto;
import com.heroland.competition.service.HerolandTopicJoinUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public Boolean join(HerolandTopicJoinUserDP dp) {
        HerolandTopicJoinUserDP herolandTopicJoinUserDP = dp.checkAndBuildBefore();
        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
        example.createCriteria().andTopicIdEqualTo(dp.getTopicId()).andJoinUserEqualTo(dp.getJoinUser()).andStateEqualTo(dp.getState());
        List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                herolandTopicJoinUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(herolandTopicJoinUsers)){
            throw new AppSystemException(HerolandErrMsgEnum.HAS_JOINED.getErrorMessage());
        }
        HerolandTopicJoinUser herolandTopicJoinUser = BeanCopyUtils.copyByJSON(herolandTopicJoinUserDP, HerolandTopicJoinUser.class);
        return herolandTopicJoinUserMapper.insertSelective(herolandTopicJoinUser) > 0;
    }

    @Override
    public Boolean cancel(HerolandTopicJoinUserDP dp) {
        HerolandTopicJoinUserDP herolandTopicJoinUserDP = dp.checkAndBuildBefore();
        HerolandTopicJoinUserExample example = new HerolandTopicJoinUserExample();
        example.createCriteria().andTopicIdEqualTo(dp.getTopicId()).andJoinUserEqualTo(dp.getJoinUser()).andStateEqualTo(TopicJoinConstant.JOIND);
        List<HerolandTopicJoinUser> herolandTopicJoinUsers =
                herolandTopicJoinUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(herolandTopicJoinUsers)){
            throw new AppSystemException(HerolandErrMsgEnum.NOT_JOINED.getErrorMessage());
        }
        HerolandTopicJoinUser herolandTopicJoinUser = BeanCopyUtils.copyByJSON(herolandTopicJoinUserDP, HerolandTopicJoinUser.class);
        return herolandTopicJoinUserMapper.updateByPrimaryKeySelective(herolandTopicJoinUser) > 0;
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
}
