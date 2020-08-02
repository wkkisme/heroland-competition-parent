package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeroLandTopicQuestionsQo extends BaseQO implements Serializable {

    /**
     * 比赛组id
     */
    private List<Long> topicIds;

    /**
     * 是否需要detail表中的详情
     */
    private Boolean includeDetail = false;

}
