package com.heroland.competition.common.contants;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;


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

    BXEDTION("BX", "oblig","必修选修"),


    DIAMOND("DA", "diamond","宝石"),

    QUEST("QE", "question","题目"),

    TOPIC("TP", "topic","题目组"),

            ;

    public static LinkedList<AdminFieldEnum> location = Lists.newLinkedList();
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

    AdminFieldEnum(String code, String field,String chName) {
        this.code = code;
        this.field = field;
        this.chName = chName;
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
            if (subjectEnum.code .equals(code)){
                return subjectEnum;
            }
        }
        return null;
    }

    public static AdminFieldEnum preAdminFieldEnumForLocation(String code){
        AdminFieldEnum adminFieldEnum = valueOfCode(code);
        if (adminFieldEnum == null){
            return null;
        }
        if (!location.contains(adminFieldEnum)){
            return null;
        }
        if (AdminFieldEnum.AREA.code.equals(code)){
            return null;
        }
        return location.get(location.indexOf(adminFieldEnum)-1);
    }

    public static AdminFieldEnum afterAdminFieldEnumForLocation(String code){
        AdminFieldEnum adminFieldEnum = valueOfCode(code);
        if (adminFieldEnum == null){
            return null;
        }
        if (!location.contains(adminFieldEnum)){
            return null;
        }
        if (AdminFieldEnum.CLASS.code.equals(code)){
            return null;
        }
        return location.get(location.indexOf(adminFieldEnum)+1);
    }

}
