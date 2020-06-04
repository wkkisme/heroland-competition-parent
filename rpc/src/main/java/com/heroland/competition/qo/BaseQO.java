package com.heroland.competition.qo;

import java.io.Serializable;

/**
 * @author wangkai
 */
public class BaseQO implements Serializable {
    private String key;

    private Integer pageIndex = 1;

    private Integer pageNum;

    private Integer pageSize = 10;


    private Integer startRow;

    private Boolean needPage = true;

    public Integer getStartRow() {
        if (pageIndex != null && pageSize != null && pageSize > 0) {
            startRow = (pageIndex - 1) * pageSize;
        }

        return startRow;
    }

    public Boolean getNeedPage() {
        return needPage;
    }

    public void setNeedPage(Boolean needPage) {

        this.needPage = needPage;
        if (needPage!= null && !needPage){
            this.pageSize= null;
            this.pageIndex= null;
            this.startRow = null;
        }
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        if (needPage) {
            this.pageIndex = pageIndex;
        }else {
            this.pageIndex = null;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (needPage) {
            this.pageSize = pageSize;
        }else {
            this.pageSize = null;
        }
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
