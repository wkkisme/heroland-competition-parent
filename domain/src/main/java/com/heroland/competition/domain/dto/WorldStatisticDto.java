package com.heroland.competition.domain.dto;

import com.google.common.collect.Maps;
import com.heroland.competition.domain.dp.HerolandStatisticsWordDP;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author smjyouzan
 * @date 2020/9/28
 */
@Data
public class WorldStatisticDto implements Serializable {

    private String userId;

    private Map<String, Integer> subjectScoreMap = Maps.newHashMap();

    private Long topicId;

    private List<HerolandStatisticsWordDP> wordDPS;
}
