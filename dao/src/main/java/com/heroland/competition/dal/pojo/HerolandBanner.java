package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandBanner")
public class HerolandBanner implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="id主键")
    private Long id;

    /**
     * 创建人
     */
    @ApiModelProperty(value="creator创建人")
    private String creator;

    /**
     * 修改人
     */
    @ApiModelProperty(value="modifier修改人")
    private String modifier;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="gmtCreate创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="gmtModified修改时间")
    private Date gmtModified;

    /**
     * 是否删除(0有效,1作废)
     */
    @ApiModelProperty(value="isDeleted是否删除(0有效,1作废)")
    private Boolean isDeleted;

    /**
     * url
     */
    @ApiModelProperty(value="urlurl")
    private String url;

    /**
     * banner_index
     */
    @ApiModelProperty(value="bannerIndexbanner_index")
    private Integer bannerIndex;

    /**
     * heroland_banner
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建人
     * @return creator 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除(0有效,1作废)
     * @return is_deleted 是否删除(0有效,1作废)
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除(0有效,1作废)
     * @param isDeleted 是否删除(0有效,1作废)
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * url
     * @return url url
     */
    public String getUrl() {
        return url;
    }

    /**
     * url
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * banner_index
     * @return banner_index banner_index
     */
    public Integer getBannerIndex() {
        return bannerIndex;
    }

    /**
     * banner_index
     * @param bannerIndex banner_index
     */
    public void setBannerIndex(Integer bannerIndex) {
        this.bannerIndex = bannerIndex;
    }
}