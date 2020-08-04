package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/15
 */
@Data
@AllArgsConstructor
public class HerolandDataPageRequest extends BaseQO implements Serializable {

    /**
     * 字典数据编号
     */
    private String code;
}
