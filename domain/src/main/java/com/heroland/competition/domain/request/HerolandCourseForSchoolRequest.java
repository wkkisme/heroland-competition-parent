package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/9/30
 */
@Data
public class HerolandCourseForSchoolRequest extends BaseQO implements Serializable {

    /**
     * 学校code
     */
    @NotNull
    private String schoolCode;

    /**
     * 年级列表code
     */
    private List<String> gradeCodeList;
}
