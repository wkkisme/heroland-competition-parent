package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smjyouzan
 * @date 2020/7/22
 */
@Data
public class HerolandDiamRecordQO extends BaseQO implements Serializable {

    private String userId;

    private Integer type;

    private String bizType;

    private String bizGroup;

    private Date beginTime;
    private Date endTime;

}
