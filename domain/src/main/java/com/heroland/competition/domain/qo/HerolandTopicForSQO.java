package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HerolandTopicForSQO extends BaseDO implements Serializable {
    /**
     * 机构code
     */
    private List<Long> topicIds;

    /**
     * 年级code
     */
    private String gradeCode;

    private String orgCode;

    private String classCode;

    private List<String> courseCodes;


}