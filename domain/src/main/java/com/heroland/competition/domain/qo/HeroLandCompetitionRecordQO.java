package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import org.apache.commons.lang3.StringUtils;

public class HeroLandCompetitionRecordQO extends BaseQO {
    private String recordId;


    public HeroLandCompetitionRecordQO queryIdCheck() {
        if (StringUtils.isAnyBlank(recordId)) {
            ResponseBodyWrapper.failParamException();
        }

        return this;
    }

    public String getRecordId() {
        return recordId;
    }

    public HeroLandCompetitionRecordQO setRecordId(String recordId) {
        this.recordId = recordId;
        return this;
    }
}
