package com.heroland.competition.factory;

import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Maps;
import com.heroland.competition.service.HeroLandCompetitionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取不同比赛类型的比赛接口
 *
 * @author wangkai
 * @date new date()
 */
@Component
public class CompetitionTopicFactory implements BeanPostProcessor {
    private static final Map<Integer, HeroLandCompetitionService> COMPETITION_SERVICE_HASH_MAP = Maps.newHashMap();

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (bean instanceof HeroLandCompetitionService) {
            COMPETITION_SERVICE_HASH_MAP.put(((HeroLandCompetitionService) bean).getType(), (HeroLandCompetitionService) bean);
        }
        return bean;
    }

    public static HeroLandCompetitionService get(Integer competitionType) {
        HeroLandCompetitionService heroLandCompetitionService = COMPETITION_SERVICE_HASH_MAP.get(competitionType);
        if (heroLandCompetitionService == null){
            ResponseBodyWrapper.failException("不存在该类型比赛");
        }
        return heroLandCompetitionService;
    }
}
