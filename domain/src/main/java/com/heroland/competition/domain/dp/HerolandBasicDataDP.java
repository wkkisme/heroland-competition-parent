package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;


@Data
public class HerolandBasicDataDP extends BaseDO implements Serializable {
    /**
     * 后台管理类型
     */
    private String code;

    /**
     * 后台管理值域
     */
    private String field;

    /**
     * 中文名称
     */
    private String chName;

    /**
     * 字典数据key
     */
    private String dictKey;

    /**
     * 字典数据名称
     */
    private String dictValue;


    public HerolandBasicDataDP checkAndBuildBeforeCreate() {
        if (StringUtils.isBlank(this.getCode()) || StringUtils.isBlank(this.getDictValue())) {
            ResponseBodyWrapper.failParamException();
        }
        AdminFieldEnum adminFieldEnum = AdminFieldEnum.valueOfCode(this.getCode());
        if (Objects.isNull(adminFieldEnum)){
            ResponseBodyWrapper.failParamException();
        }
        this.setField(adminFieldEnum.getField());
        this.setChName(adminFieldEnum.getChName());
        this.setDictKey(IDGenerateUtils.getKey(adminFieldEnum));
        return this;
    }

    public HerolandBasicDataDP checkAndBuildBeforeUpdate() {
        if (NumberUtils.nullOrZeroLong(this.getId())) {
            ResponseBodyWrapper.failParamException();
        }
        return this;
    }
}
