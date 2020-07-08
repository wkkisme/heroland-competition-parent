package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class HerolandOrderQueryQO implements Serializable {

    /**
     * 购买id
     */
    private String buyerId;

    /**
     * 状态
     */
    private List<String> status;
}
