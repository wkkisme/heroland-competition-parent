package com.heroland.competition.common.enums;

/**
 * 邀请的状态枚举
 * @author mac
 */

public enum InviteStatusEnum {
    /**
     * 同意
     */
    AGREE(0),
    /**
     * 不同意
     */
    DO_NOT_AGREE(1);

    private  Integer status;

    InviteStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
