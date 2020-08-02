package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author wushuaiping
 * @date 2020/8/2 22:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerResultQO extends BaseQO implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 比赛记录id
     */
    private String competitionRecordId;

}
