package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class HeroLandQuestionQO extends BaseQO {

    @MybatisCriteriaAnnotation
    private Long topicId;

    @MybatisCriteriaAnnotation
    private Long questionId;
}
