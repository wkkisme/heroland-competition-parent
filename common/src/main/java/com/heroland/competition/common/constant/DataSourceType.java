package com.heroland.competition.common.constant;

/**
 * @author Eason(bo.chenb)
 * @description
 *  数据源类型
 * @date 2020-03-11
 **/
public enum DataSourceType {

    mysql,
    oracle,
    odps,
    elasticsearch,
    ;


    public static DataSourceType parse(String type){

        for (DataSourceType dataSourceType : DataSourceType.values()){
            if(dataSourceType.name().equalsIgnoreCase(type)){
                return dataSourceType;
            }
        }

        return null;
    }
}
