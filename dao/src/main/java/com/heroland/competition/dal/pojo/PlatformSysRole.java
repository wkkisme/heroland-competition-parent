package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.PlatformSysRole")
public class PlatformSysRole extends BaseDO implements Serializable {
    /**
     * 角色名称
     */
    @ApiModelProperty(value="roleName角色名称")
    private String roleName;

    /**
     * 角色code
     */
    @ApiModelProperty(value="roleCode角色code")
    private String roleCode;

    /**
     * 类型
     */
    @ApiModelProperty(value="roleType类型")
    private String roleType;

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
     * platform_sys_role
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     * @return role_name 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 角色名称
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 角色code
     * @return role_code 角色code
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 角色code
     * @param roleCode 角色code
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * 类型
     * @return role_type 类型
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * 类型
     * @param roleType 类型
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

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
}