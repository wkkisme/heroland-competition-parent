package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/19
 */
@Data
public class HerolandTopicCanSeeQO extends BaseQO implements Serializable {

    /**
     *  用户id
     */
    @NotNull
    private String userId;


    /**
     * 赛事类型
     */
    private Integer topicType;

    /**
     * 赛事的时间状态
     * 正在进行的 DOING
     * 已经过期的 OVERDUE
     * 还未开始的 NOTSTART
     */
    private String topicState;
}
