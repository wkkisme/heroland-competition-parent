package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.MappingChapter")
public class MappingChapter implements Serializable {
    /**
     * 
     */
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 
     */
    @ApiModelProperty(value="subjectid")
    private Integer subjectid;

    /**
     * 
     */
    @ApiModelProperty(value="pharseid")
    private Integer pharseid;

    /**
     * 
     */
    @ApiModelProperty(value="gradeid")
    private Integer gradeid;

    /**
     * 
     */
    @ApiModelProperty(value="editionid")
    private Integer editionid;

    /**
     * 
     */
    @ApiModelProperty(value="chapter")
    private String chapter;

    /**
     * 
     */
    @ApiModelProperty(value="unit")
    private String unit;

    /**
     * 
     */
    @ApiModelProperty(value="section")
    private String section;

    /**
     * 
     */
    @ApiModelProperty(value="knowledgeid")
    private Integer knowledgeid;

    /**
     * 
     */
    @ApiModelProperty(value="chapterorder")
    private Integer chapterorder;

    /**
     * 
     */
    @ApiModelProperty(value="unitorder")
    private Integer unitorder;

    /**
     * 
     */
    @ApiModelProperty(value="sectionorder")
    private Integer sectionorder;

    /**
     * mapping_chapter
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
     * 
     * @return subjectId 
     */
    public Integer getSubjectid() {
        return subjectid;
    }

    /**
     * 
     * @param subjectid 
     */
    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * 
     * @return pharseId 
     */
    public Integer getPharseid() {
        return pharseid;
    }

    /**
     * 
     * @param pharseid 
     */
    public void setPharseid(Integer pharseid) {
        this.pharseid = pharseid;
    }

    /**
     * 
     * @return gradeId 
     */
    public Integer getGradeid() {
        return gradeid;
    }

    /**
     * 
     * @param gradeid 
     */
    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    /**
     * 
     * @return editionId 
     */
    public Integer getEditionid() {
        return editionid;
    }

    /**
     * 
     * @param editionid 
     */
    public void setEditionid(Integer editionid) {
        this.editionid = editionid;
    }

    /**
     * 
     * @return chapter 
     */
    public String getChapter() {
        return chapter;
    }

    /**
     * 
     * @param chapter 
     */
    public void setChapter(String chapter) {
        this.chapter = chapter == null ? null : chapter.trim();
    }

    /**
     * 
     * @return unit 
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit 
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 
     * @return section 
     */
    public String getSection() {
        return section;
    }

    /**
     * 
     * @param section 
     */
    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    /**
     * 
     * @return knowledgeId 
     */
    public Integer getKnowledgeid() {
        return knowledgeid;
    }

    /**
     * 
     * @param knowledgeid 
     */
    public void setKnowledgeid(Integer knowledgeid) {
        this.knowledgeid = knowledgeid;
    }

    /**
     * 
     * @return chapterOrder 
     */
    public Integer getChapterorder() {
        return chapterorder;
    }

    /**
     * 
     * @param chapterorder 
     */
    public void setChapterorder(Integer chapterorder) {
        this.chapterorder = chapterorder;
    }

    /**
     * 
     * @return unitOrder 
     */
    public Integer getUnitorder() {
        return unitorder;
    }

    /**
     * 
     * @param unitorder 
     */
    public void setUnitorder(Integer unitorder) {
        this.unitorder = unitorder;
    }

    /**
     * 
     * @return sectionOrder 
     */
    public Integer getSectionorder() {
        return sectionorder;
    }

    /**
     * 
     * @param sectionorder 
     */
    public void setSectionorder(Integer sectionorder) {
        this.sectionorder = sectionorder;
    }
}