package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2020/7/12
 */
@Data
public class HeroLandQuestionTopicListDto implements Serializable {

    /**
     * id
     */
    private Long id;


    /**
     * 关联的题号和知识点列表
     */
    private List<HeroLandQuestionListForTopicDto> questions = Lists.newArrayList();

}
