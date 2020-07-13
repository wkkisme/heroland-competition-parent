package com.heroland.competition.factory;

import com.google.common.collect.Maps;
import com.heroland.competition.api.HeroLandCompetitionService;
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
    private static Map<Integer, HeroLandCompetitionService> competitionServiceHashMap = Maps.newHashMap();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HeroLandCompetitionService) {
            competitionServiceHashMap.put(((HeroLandCompetitionService) bean).getType(), (HeroLandCompetitionService) bean);
        }
        return bean;
    }

    public static HeroLandCompetitionService get(Integer competitionType) {
        return competitionServiceHashMap.get(competitionType);
    }
}
