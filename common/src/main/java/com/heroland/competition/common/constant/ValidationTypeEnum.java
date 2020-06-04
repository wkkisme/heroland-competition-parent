package com.heroland.competition.common.constant;

/**
 * 校验类型
 *
 * @author Eason@bianque
 * @date 2018/12/03
 **/
public enum ValidationTypeEnum {

    /*0 字典校验 1 非空校验 2 值域校验 3 规范校验 4 逻辑校验 5 唯一校验*/
    ValidationType_Dic(0,"字典校验",
            "select count(*) as expect, " +
                    "count(case when [col] in (select [dict_col] from [dict_table]) then 1 else null end) as actual " +
                    "from [table_name]"),
    ValidationType_NotNull(1,"非空校验", "select count(*) as expect," +
            "count(case when [col1] is not null and [col2] is not null then 1 else null end) as actual " +
            "from [table_name]"),
    ValidationType_Rang(2,"值域校验", "select count(*) as expect," +
            "count(case when [col] in ('[value1]','[value2]') then 1 else null end)  as actual " +
            "from [table_name]"),
    ValidationType_Standard(3,"规范校验", "select count(*) as expect," +
            "count(case when [col] rlike '[expression]' then 1 else null end) as actual " +
            "from [table_name]"),
    ValidationType_Logical(4,"逻辑校验", "select count(*) as expect," +
            "count(case when [expression] then 1 else null end) as actual " +
            "from [table_name]"),
    ValidationType_Only(5,"唯一校验", "select count(*) as  expect," +
            "count(distinct [col1],[col2]) as actual from [table_name]");

    private int type;

    private String name;
    /**
     * Sql 模板
     */
    private String template;

    private static final String commonStartSQL = "";

    ValidationTypeEnum(int type, String name, String template) {
        this.type = type;
        this.name = name;
        this.template = template;
    }

    public static ValidationTypeEnum getTypeEnum(Integer type) {

        for (ValidationTypeEnum typeEnum : ValidationTypeEnum.values()){
            if(typeEnum.getType() == type){
                return typeEnum;
            }
        }

        return null;

    }

    public static String getTypeName(Integer type) {
        for (ValidationTypeEnum typeEnum : ValidationTypeEnum.values()){
            if(typeEnum.getType() == type){
                return typeEnum.getName();
            }
        }

        return null;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }
}
