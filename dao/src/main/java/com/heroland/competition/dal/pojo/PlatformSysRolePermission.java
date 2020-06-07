package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.PlatformSysRolePermission")
public class PlatformSysRolePermission extends BaseDO implements Serializable {
    /**
     * 角色code
     */
    @ApiModelProperty(value="roleId角色code")
    private String roleId;

    /**
     * 权限id
     */
    @ApiModelProperty(value="permissionId权限id")
    private String permissionId;

    /**
     * platform_sys_role_permission
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色code
     * @return role_id 角色code
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 角色code
     * @param roleId 角色code
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
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
}