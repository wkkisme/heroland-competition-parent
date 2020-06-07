package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.PlatformSysUserRole")
public class PlatformSysUserRole extends BaseDO implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value="userId用户id")
    private String userId;

    /**
     * role id
     */
    @ApiModelProperty(value="roleIdrole id")
    private String roleId;

    /**
     * platform_sys_user_role
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * role id
     * @return role_id role id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * role id
     * @param roleId role id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}