package com.heroland.competition.domain.dto;

import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/6
 */
@Data
public class HerolandLocationDto implements Serializable {

    /**
     * 编号
     */
    private String code;

    /**
     * 编号值
     */
    private String field;

    private Boolean isArea;

    private List<HerolandBasicDataDP> areaList;

    /**
     * 当前空间编号
     */
    private String currentKey;


    /**
     * 下一层级的空间列表
     */
    private List<HerolandBasicDataDP> childLocation;

    public Boolean getIsArea(){
        return (StringUtils.isNotBlank(code) && AdminFieldEnum.AREA.getCode().equals(code)) ? Boolean.TRUE : Boolean.FALSE;
    }

}
