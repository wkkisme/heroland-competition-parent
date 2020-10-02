package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/10/2
 */
@Data
public class ParentBySubNodeRequest implements Serializable {

    private List<String> keys;
    private String code;
}
