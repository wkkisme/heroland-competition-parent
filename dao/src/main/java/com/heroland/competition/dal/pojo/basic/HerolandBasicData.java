package com.heroland.competition.dal.pojo.basic;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandBasicData extends BaseDO implements Serializable {

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

    /**
     * 业务编号，如香港的学校编号
     */
    private String bizNo;

    /**
     * 国际化表示--比如学校的英文表示
     */
    private String bizI18N;


    //以下两字段只为刷数据使用
    private String mappingId;
    private String mappingKey;

}