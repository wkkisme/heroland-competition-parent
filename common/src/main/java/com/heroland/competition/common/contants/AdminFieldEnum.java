package com.heroland.competition.common.contants;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


/**
 * @author smjyouzan
 * @date 2020/6/22
 */
public enum AdminFieldEnum {
    //地区
    AREA("AE", "area","地区"),
    //学校
    SCHOOL("SH", "school","学校"),
    //班级
    CLASS("CA", "class","班级"),


    //阶段
    PHASE("PA", "phase","阶段"),
    //年级
    GRADE("GA", "grade","年级"),
    //科目
    COURSE("CU", "course","科目"),
    //版本
    EDITION("ED", "edition","版本"),
    //章节
    CHAPTER("CP", "chapter","章节"),
    //难度
    DIFFICULTY("DF", "diff","难度"),
    //知识点
    KNOWEDGE("KL", "knowledge","知识点"),

            ;



    @Getter
    private String field;
    @Getter
    private String chName;

    @Getter
    private String code;

    AdminFieldEnum(String code, String chName, String field) {
        this.chName = chName;
        this.field = field;
        this.code = code;
    }

    public static AdminFieldEnum valueOfName(String field) {
        if (field == null) {
            return null;
        }
        for (AdminFieldEnum subjectEnum : values()){
            if (subjectEnum.field.equals(field)){
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
