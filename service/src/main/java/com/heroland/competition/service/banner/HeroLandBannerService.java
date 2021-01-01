package com.heroland.competition.service.banner;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.dal.pojo.HerolandBanner;
import com.heroland.competition.domain.qo.HerolandBannerQO;

import java.util.List;

public interface HeroLandBannerService {

    ResponseBody<Boolean> save(HerolandBanner herolandBanner);

    ResponseBody<Boolean> delete(HerolandBanner herolandBanner);

    ResponseBody<Boolean> update(HerolandBanner herolandBanner);

    ResponseBody<List<HerolandBanner>> get(HerolandBannerQO herolandBanner);

}
