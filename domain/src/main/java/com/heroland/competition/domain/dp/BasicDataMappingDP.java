package com.heroland.competition.domain.dp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/10/29
 */
@Data
public class BasicDataMappingDP implements Serializable {

    private String id;
    private String dataTypeId;
    private String name;
    private String code;
}
