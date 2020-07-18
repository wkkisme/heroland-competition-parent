package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandSchoolCourseRequest implements Serializable {

    /**
     * 学校code
     */
    @NotNull
    private String schoolCode;

    /**
     * 必修|选修
     * 1 必修 2 选修
     */
    private Integer obligatory;
}
