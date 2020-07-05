package com.heroland.competition.common.contants;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


/**
 * @author smjyouzan
 * @date 2020/6/22
 */
public enum AdminFieldEnum {
    //地区
    AREA("AE", "area"),
    //学校
    SCHOOL("SH", "school"),
    //班级
    CLASS("CA", "class"),


    //阶段
    PHASE("PA", "phase"),
    //年级
    GRADE("GA", "grade"),
    //科目
    COURSE("CU", "course"),
    //版本
    EDITION("ED", "edition"),
    //章节
    CHAPTER("CP", "chapter"),
    //难度
    DIFFICULTY("DF", "diff"),
    //知识点
    KNOWEDGE("KL", "knowledge"),

            ;



    @Getter
    private String name;

    @Getter
    private String code;

    AdminFieldEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public static AdminFieldEnum valueOfName(String name) {
        if (name == null) {
            return null;
        }
        for (AdminFieldEnum subjectEnum : values()){
            if (subjectEnum.name.equals(name)){
                return subjectEnum;
            }
        }
        return null;
    }

    public static AdminFieldEnum valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (AdminFieldEnum subjectEnum : values()){
            if (subjectEnum.code == code){
                return subjectEnum;
            }
        }
        return null;
    }

}
