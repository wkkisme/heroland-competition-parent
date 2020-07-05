package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @date 2020/6/23
 */
@Data
public class HerolandLocationDP extends BaseDO implements Serializable {
    /**
     * 地区编号
     */
    private String area;

    /**
     * 学校编号
     */
    private String school;

    /**
     * 年级编号
     */
    private String grade;

    /**
     * 班级编号
     */
    private String clas;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级名称
     */
    private String clasName;




    public HerolandLocationDP check() {
        AssertUtils.notBlank(this.getClas());
        AssertUtils.notBlank(this.getGrade());
        AssertUtils.notBlank(this.getSchool());
        AssertUtils.notBlank(this.getArea());
        return this;
    }
}
