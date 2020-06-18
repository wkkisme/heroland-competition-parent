package com.heroland.competition.netty;


/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class UserWebsocketSalt {
    /**
     * userId
     */
    private String userId;

    /**
     * loginLabel 当前登录标签
     */
    private String loginLabel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginLabel() {
        return loginLabel;
    }

    public void setLoginLabel(String loginLabel) {
        this.loginLabel = loginLabel;
    }
}
