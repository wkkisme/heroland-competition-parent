package com.heroland.competition.remote;

import com.heroland.competition.api.HeroLandDicRemoteService;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.service.admin.HeroLandAdminService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(version = "1.0.0")
public class HeroLandDicRemoteServiceImpl implements HeroLandDicRemoteService {

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    public List<HerolandBasicDataDP> getDictInfoByKeys(List<String> keys) {
        return heroLandAdminService.getDictInfoByKeys(keys);
    }
}
