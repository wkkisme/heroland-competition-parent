package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class HerolandBasicDataRequest implements Serializable {

    /**
     * 如果是编辑则必传
     */
    private Long id;

    /**
     * 后台管理类型
     */
    @NotNull
    private String code;

    /**
     * 字典数据名称
     */
    @NotNull
    private String dictValue;

}
