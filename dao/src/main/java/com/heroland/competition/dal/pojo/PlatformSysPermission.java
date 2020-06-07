package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.PlatformSysPermission")
public class PlatformSysPermission extends BaseDO implements Serializable {
    /**
     * appid
     */
    @ApiModelProperty(value="appIdappid")
    private String appId;

    /**
     * 学校组织code
     */
    @ApiModelProperty(value="orgCode学校组织code")
    private String orgCode;

    /**
     * 资源路径
     */
    @ApiModelProperty(value="sourceUrl资源路径")
    private String sourceUrl;

    /**
     * 权限id
     */
    @ApiModelProperty(value="permissionId权限id")
    private String permissionId;

    /**
     * 权限名称
     */
    @ApiModelProperty(value="permissionName权限名称")
    private String permissionName;

    /**
     * 父级
     */
    @ApiModelProperty(value="parent父级")
    private String parent;

    /**
     * 0 菜单 1接口
     */
    @ApiModelProperty(value="type0 菜单 1接口")
    private Integer type;

    /**
     * platform_sys_permission
     */
    private static final long serialVersionUID = 1L;

    /**
     * appid
     * @return app_id appid
     */
    public String getAppId() {
        return appId;
    }

    /**
     * appid
     * @param appId appid
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 学校组织code
     * @return org_code 学校组织code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 学校组织code
     * @param orgCode 学校组织code
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 资源路径
     * @return source_url 资源路径
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * 资源路径
     * @param sourceUrl 资源路径
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    /**
     * 权限id
     * @return permission_id 权限id
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * 权限id
     * @param permissionId 权限id
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    /**
     * 权限名称
     * @return permission_name 权限名称
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * 权限名称
     * @param permissionName 权限名称
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * 父级
     * @return parent 父级
     */
    public String getParent() {
        return parent;
    }

    /**
     * 父级
     * @param parent 父级
     */
    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    /**
     * 0 菜单 1接口
     * @return type 0 菜单 1接口
     */
    public Integer getType() {
        return type;
    }

    /**
     * 0 菜单 1接口
     * @param type 0 菜单 1接口
     */
    public void setType(Integer type) {
        this.type = type;
    }
}