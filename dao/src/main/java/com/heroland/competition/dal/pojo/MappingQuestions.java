package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.MappingQuestions")
public class MappingQuestions implements Serializable {
    /**
     * 
     */
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 试题题型
     */
    @ApiModelProperty(value="qtpye试题题型")
    private String qtpye;

    /**
     * 
     */
    @ApiModelProperty(value="diff")
    private Float diff;

    /**
     * 试题题干的md5值
     */
    @ApiModelProperty(value="md5试题题干的md5值")
    private String md5;

    /**
     * 学科Id
     */
    @ApiModelProperty(value="subjectid学科Id")
    private Byte subjectid;

    /**
     * 年级ID
     */
    @ApiModelProperty(value="gradeid年级ID")
    private Integer gradeid;

    /**
     * 
     */
    @ApiModelProperty(value="knowledges")
    private String knowledges;

    /**
     * 试题区域
     */
    @ApiModelProperty(value="area试题区域")
    private String area;

    /**
     * 试题年份
     */
    @ApiModelProperty(value="year试题年份")
    private Integer year;

    /**
     * 试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他
     */
    @ApiModelProperty(value="papertpye试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他")
    private String papertpye;

    /**
     * 试题来源(试卷)
     */
    @ApiModelProperty(value="source试题来源(试卷)")
    private String source;

    /**
     * 试题来源（网站）
     */
    @ApiModelProperty(value="fromsite试题来源（网站）")
    private String fromsite;

    /**
     * 是否存在图片水印
     */
    @ApiModelProperty(value="issub是否存在图片水印")
    private Boolean issub;

    /**
     * 是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1
     */
    @ApiModelProperty(value="isnormal是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1")
    private Boolean isnormal;

    /**
     * 是否匹配章节知识点，1匹配，0不匹配
     */
    @ApiModelProperty(value="iskonw是否匹配章节知识点，1匹配，0不匹配")
    private Boolean iskonw;

    /**
     * 试题的tiid，结合fromsite进行同网站试题排重，用于增量更新
     */
    @ApiModelProperty(value="tiid试题的tiid，结合fromsite进行同网站试题排重，用于增量更新")
    private String tiid;

    /**
     * 试题在题库中的相似度，相似度越高，质量越低
     */
    @ApiModelProperty(value="similarity试题在题库中的相似度，相似度越高，质量越低")
    private Integer similarity;

    /**
     * 
     */
    @ApiModelProperty(value="isunique")
    private Byte isunique;

    /**
     * 
     */
    @ApiModelProperty(value="md52")
    private String md52;

    /**
     * mapping_questions
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
     * 试题题型
     * @return qtpye 试题题型
     */
    public String getQtpye() {
        return qtpye;
    }

    /**
     * 试题题型
     * @param qtpye 试题题型
     */
    public void setQtpye(String qtpye) {
        this.qtpye = qtpye == null ? null : qtpye.trim();
    }

    /**
     * 
     * @return diff 
     */
    public Float getDiff() {
        return diff;
    }

    /**
     * 
     * @param diff 
     */
    public void setDiff(Float diff) {
        this.diff = diff;
    }

    /**
     * 试题题干的md5值
     * @return md5 试题题干的md5值
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 试题题干的md5值
     * @param md5 试题题干的md5值
     */
    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    /**
     * 学科Id
     * @return subjectId 学科Id
     */
    public Byte getSubjectid() {
        return subjectid;
    }

    /**
     * 学科Id
     * @param subjectid 学科Id
     */
    public void setSubjectid(Byte subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * 年级ID
     * @return gradeId 年级ID
     */
    public Integer getGradeid() {
        return gradeid;
    }

    /**
     * 年级ID
     * @param gradeid 年级ID
     */
    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    /**
     * 
     * @return knowledges 
     */
    public String getKnowledges() {
        return knowledges;
    }

    /**
     * 
     * @param knowledges 
     */
    public void setKnowledges(String knowledges) {
        this.knowledges = knowledges == null ? null : knowledges.trim();
    }

    /**
     * 试题区域
     * @return area 试题区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 试题区域
     * @param area 试题区域
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 试题年份
     * @return year 试题年份
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 试题年份
     * @param year 试题年份
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他
     * @return paperTpye 试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他
     */
    public String getPapertpye() {
        return papertpye;
    }

    /**
     * 试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他
     * @param papertpye 试题类型：1，月考；2，模拟考；3，中考；4，高考；5，学业考试；6，其他
     */
    public void setPapertpye(String papertpye) {
        this.papertpye = papertpye == null ? null : papertpye.trim();
    }

    /**
     * 试题来源(试卷)
     * @return source 试题来源(试卷)
     */
    public String getSource() {
        return source;
    }

    /**
     * 试题来源(试卷)
     * @param source 试题来源(试卷)
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 试题来源（网站）
     * @return fromSite 试题来源（网站）
     */
    public String getFromsite() {
        return fromsite;
    }

    /**
     * 试题来源（网站）
     * @param fromsite 试题来源（网站）
     */
    public void setFromsite(String fromsite) {
        this.fromsite = fromsite == null ? null : fromsite.trim();
    }

    /**
     * 是否存在图片水印
     * @return isSub 是否存在图片水印
     */
    public Boolean getIssub() {
        return issub;
    }

    /**
     * 是否存在图片水印
     * @param issub 是否存在图片水印
     */
    public void setIssub(Boolean issub) {
        this.issub = issub;
    }

    /**
     * 是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1
     * @return isNormal 是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1
     */
    public Boolean getIsnormal() {
        return isnormal;
    }

    /**
     * 是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1
     * @param isnormal 是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1
     */
    public void setIsnormal(Boolean isnormal) {
        this.isnormal = isnormal;
    }

    /**
     * 是否匹配章节知识点，1匹配，0不匹配
     * @return isKonw 是否匹配章节知识点，1匹配，0不匹配
     */
    public Boolean getIskonw() {
        return iskonw;
    }

    /**
     * 是否匹配章节知识点，1匹配，0不匹配
     * @param iskonw 是否匹配章节知识点，1匹配，0不匹配
     */
    public void setIskonw(Boolean iskonw) {
        this.iskonw = iskonw;
    }

    /**
     * 试题的tiid，结合fromsite进行同网站试题排重，用于增量更新
     * @return tiid 试题的tiid，结合fromsite进行同网站试题排重，用于增量更新
     */
    public String getTiid() {
        return tiid;
    }

    /**
     * 试题的tiid，结合fromsite进行同网站试题排重，用于增量更新
     * @param tiid 试题的tiid，结合fromsite进行同网站试题排重，用于增量更新
     */
    public void setTiid(String tiid) {
        this.tiid = tiid == null ? null : tiid.trim();
    }

    /**
     * 试题在题库中的相似度，相似度越高，质量越低
     * @return Similarity 试题在题库中的相似度，相似度越高，质量越低
     */
    public Integer getSimilarity() {
        return similarity;
    }

    /**
     * 试题在题库中的相似度，相似度越高，质量越低
     * @param similarity 试题在题库中的相似度，相似度越高，质量越低
     */
    public void setSimilarity(Integer similarity) {
        this.similarity = similarity;
    }

    /**
     * 
     * @return isunique 
     */
    public Byte getIsunique() {
        return isunique;
    }

    /**
     * 
     * @param isunique 
     */
    public void setIsunique(Byte isunique) {
        this.isunique = isunique;
    }

    /**
     * 
     * @return md52 
     */
    public String getMd52() {
        return md52;
    }

    /**
     * 
     * @param md52 
     */
    public void setMd52(String md52) {
        this.md52 = md52 == null ? null : md52.trim();
    }
}