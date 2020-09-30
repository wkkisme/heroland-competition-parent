package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/9/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorldStatisticQO extends BaseQO implements Serializable {

    /**
     * 比赛id
     */
    @NotNull
    private Long topicId;

}
