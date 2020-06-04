package com.heroland.competition.util;

import com.alijk.bqcommon.base.pagination.Pagination;
import com.github.pagehelper.PageInfo;

/**
 * @author: xiezy
 * @date: 2020/1/17
 */
public class PageUtil {

    public static  <T>Pagination getPagination(PageInfo<T> pageInfo) {
        Pagination pagination = new Pagination();
        pagination.setPageNum(pageInfo.getPageNum());
        pagination.setPageSize(pageInfo.getPageSize());
        pagination.setTotal(pageInfo.getTotal());
        return pagination;
    }

    public static Pagination getPagination(int pageNum, int pageSize, long total){
        Pagination pagination = new Pagination();
        pagination.setPageNum(pageNum);
        pagination.setPageSize(pageSize);
        pagination.setTotal(total);
        return pagination;
    }
}
