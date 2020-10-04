package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolPageRequest extends BaseQO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 详细的节点key
     */
    private String parentKey;

    /**
     * 学校key
     * 只有是下拉班级时需要带上
     * 否则拉出的是所有某个年级的班级，而非某一个学校下某个年级的班级
     */
    private String schoolKey;

    /**
     * 批量key节点
     */
    private List<String> parentKeys;

    /**
     * 用户信息
     */
    private Integer roleType;

    /**
     * 用户的机构信息
     */
    private String orgCode;



}
