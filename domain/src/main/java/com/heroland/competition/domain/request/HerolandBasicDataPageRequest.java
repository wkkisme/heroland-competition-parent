package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/6/23
 */
@Data
public class HerolandBasicDataPageRequest extends BaseQO implements Serializable {

    /**
     * 后台管理类型
     */
    private List<String> code;

    /**
     * 字典数据名称
     * 模糊查询
     */
    private String name;



}
