package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.dal.pojo.HeroLandUserCompetition;

public interface HeroLandAccountExtMapper extends HeroLandAccountMapper{
    HeroLandUserCompetition getCurrentUserCompetition(HeroLandAccount conversion);
}