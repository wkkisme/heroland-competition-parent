package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/12
 */
@Data
public class QuestionOptionDto implements Serializable {

    /**
     * 选项名称 A,B,C...
     */
    private String optionSerial;

    /**
     * 选项内容
     */
    private String content;
}
