package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandSchoolCourseDP extends BaseDO implements Serializable {

    /**
     * 学校code
     */
    private String schoolCode;

    /**
     * 必修|选修
     * 1 必修 2 选修
     */
//    private Integer obligatory;
}
