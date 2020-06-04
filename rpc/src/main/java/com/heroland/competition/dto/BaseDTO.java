package com.heroland.competition.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangkai
 */
public class BaseDTO implements Serializable {
    private Date gmtModified;

    private String modifier;

    private Date gmtCreate;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "gmtModified=" + gmtModified +
                ", modifier='" + modifier + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
