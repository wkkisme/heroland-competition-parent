package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class HerolandOrderQueryQO extends BaseQO implements Serializable {

    /**
     * 购买id
     */
    @NotNull
    private String buyerId;

    /**
     * 状态
     */
    private List<String> status;
}
