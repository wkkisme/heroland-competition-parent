package com.heroland.competition.common.constants;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;


/**
 * @author smjyouzan
 * @date 2020/6/22
 */
public enum GradeEnum {
    //地区
    AREA("100", "area","地区"),
    //学校
    SCHOOL("SH", "school","学校"),
    //班级
    CLASS("CA", "clazz","班级"),


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


    DIAMOND("DA", "diamond","宝石"),

            ;

    public static LinkedList<GradeEnum> location = Lists.newLinkedList();
    static {
        location.add(0,AREA);
        location.add(1,SCHOOL);
        location.add(2,GRADE);
        location.add(3,CLASS);
    }

    @Getter
    private String code;
    @Getter
    private String field;
    @Getter
    private String chName;

    GradeEnum(String code, String field, String chName) {
        this.code = code;
        this.field = field;
        this.chName = chName;
    }

    public static GradeEnum valueOfName(String field) {
        if (field == null) {
            return null;
        }
        for (GradeEnum subjectEnum : values()){
            if (subjectEnum.field.equals(field)){
                return subjectEnum;
            }
        }
        return null;
    }

    public static GradeEnum valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (GradeEnum subjectEnum : values()){
            if (subjectEnum.code .equals(code)){
                return subjectEnum;
            }
        }
        return null;
    }

    public static GradeEnum preAdminFieldEnumForLocation(String code){
        GradeEnum adminFieldEnum = valueOfCode(code);
        if (adminFieldEnum == null){
            return null;
        }
        if (!location.contains(adminFieldEnum)){
            return null;
        }
        if (GradeEnum.AREA.code.equals(code)){
            return null;
        }
        return location.get(location.indexOf(adminFieldEnum)-1);
    }

    public static GradeEnum afterAdminFieldEnumForLocation(String code){
        GradeEnum adminFieldEnum = valueOfCode(code);
        if (adminFieldEnum == null){
            return null;
        }
        if (!location.contains(adminFieldEnum)){
            return null;
        }
        if (GradeEnum.CLASS.code.equals(code)){
            return null;
        }
        return location.get(location.indexOf(adminFieldEnum)+1);
    }

}
