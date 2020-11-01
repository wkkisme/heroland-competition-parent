package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.MappingKnowledge")
public class MappingKnowledge implements Serializable {
    /**
     * 
     */
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 知识点名称
     */
    @ApiModelProperty(value="knowledgename知识点名称")
    private String knowledgename;

    /**
     * 学科ID
     */
    @ApiModelProperty(value="subjectid学科ID")
    private Integer subjectid;

    /**
     * 学历Id
     */
    @ApiModelProperty(value="pharseid学历Id")
    private Integer pharseid;

    /**
     * mapping_knowledge
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 知识点名称
     * @return knowledgeName 知识点名称
     */
    public String getKnowledgename() {
        return knowledgename;
    }

    /**
     * 知识点名称
     * @param knowledgename 知识点名称
     */
    public void setKnowledgename(String knowledgename) {
        this.knowledgename = knowledgename == null ? null : knowledgename.trim();
    }

    /**
     * 学科ID
     * @return subjectId 学科ID
     */
    public Integer getSubjectid() {
        return subjectid;
    }

    /**
     * 学科ID
     * @param subjectid 学科ID
     */
    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * 学历Id
     * @return pharseId 学历Id
     */
    public Integer getPharseid() {
        return pharseid;
    }

    /**
     * 学历Id
     * @param pharseid 学历Id
     */
    public void setPharseid(Integer pharseid) {
        this.pharseid = pharseid;
    }
}