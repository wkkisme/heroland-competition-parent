package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.utils.IDGenerateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author smjyouzan
 * @date 2020/6/23
 */
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
     * 字典数据key
     */
    private String dictKey;

    /**
     * 字典数据名称
     */
    private String dictValue;


    public HerolandBasicDataDP checkAndBuild() {
        if (StringUtils.isBlank(this.getCode()) || StringUtils.isBlank(this.getDictValue())) {
            ResponseBodyWrapper.failParamException();
        }
        AdminFieldEnum adminFieldEnum = AdminFieldEnum.valueOfCode(this.getCode());
        if (Objects.isNull(adminFieldEnum)){
            ResponseBodyWrapper.failParamException();
        }
        this.setField(adminFieldEnum.getName());
        this.setDictKey(IDGenerateUtils.getKey(adminFieldEnum));

        return this;
    }
}
