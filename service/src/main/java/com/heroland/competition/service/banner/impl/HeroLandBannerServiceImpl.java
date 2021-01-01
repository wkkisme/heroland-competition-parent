package com.heroland.competition.service.banner.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.dal.mapper.HerolandBannerMapper;
import com.heroland.competition.dal.pojo.HerolandBanner;
import com.heroland.competition.dal.pojo.HerolandBannerExample;
import com.heroland.competition.domain.qo.HerolandBannerQO;
import com.heroland.competition.service.banner.HeroLandBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HeroLandBannerServiceImpl implements HeroLandBannerService {
    @Resource
    private HerolandBannerMapper herolandBannerMapper;

    @Override
    public ResponseBody<Boolean> save(HerolandBanner herolandBanner) {
        return ResponseBodyWrapper.successWrapper(herolandBannerMapper.insert(herolandBanner)>0);
    }

    @Override
    public ResponseBody<Boolean> delete(HerolandBanner herolandBanner) {
        return ResponseBodyWrapper.successWrapper(herolandBannerMapper.deleteByPrimaryKey(herolandBanner.getId())>0);
    }

    @Override
    public ResponseBody<Boolean> update(HerolandBanner herolandBanner) {
        return ResponseBodyWrapper.successWrapper(herolandBannerMapper.updateByPrimaryKey(herolandBanner)>0);
    }

    @Override
    public ResponseBody<List<HerolandBanner>> get(HerolandBannerQO qo) {
        HerolandBannerExample herolandBannerExample = new HerolandBannerExample();
        MybatisCriteriaConditionUtil.createExample(herolandBannerExample.createCriteria(),qo);
        List<HerolandBanner> herolandBanners = herolandBannerMapper.selectByExample(herolandBannerExample);
        return ResponseBodyWrapper.successListWrapper(herolandBanners,(long)herolandBanners.size(),qo,HerolandBanner.class);
    }
}
