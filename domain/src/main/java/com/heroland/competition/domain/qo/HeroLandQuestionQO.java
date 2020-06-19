package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import org.apache.commons.lang3.StringUtils;

public class HeroLandQuestionQO extends BaseQO {

    @MybatisCriteriaAnnotation
    private String topicId;

    @MybatisCriteriaAnnotation
    private String questionId;

    @MybatisCriteriaAnnotation
    private String orgCode;



    public HeroLandQuestionQO topicIdCheck(){

        if (StringUtils.isBlank(this.topicId)){
            ResponseBodyWrapper.failSysException();
        }
        return this;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
