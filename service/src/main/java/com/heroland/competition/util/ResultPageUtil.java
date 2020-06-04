package com.heroland.competition.util;

import com.alijk.bq.page.Pagination;
import com.alijk.bq.result.ResponseWrapper;
import com.alijk.bqhealth.cloud.dal.qo.BaseQO;

/**
 * @author wangkai
 */
public class ResultPageUtil {


    /**
     * @param respWrapper
     * @param qo
     * @param <T>
     */
    public static <T extends BaseQO> void setPage(ResponseWrapper respWrapper, T qo,Long total){

        Pagination pagination = new Pagination();
        if (qo.getPageIndex() != null) {
            pagination.setPageIndex(qo.getPageIndex());
        }
        if (qo.getPageSize() != null) {
            pagination.setPageSize(qo.getPageSize());
        }
        if (total != null) {
            pagination.setTotal(total);
        }
        respWrapper.setPage(pagination);


    }   /**
     * @param respWrapper
     * @param qo
     * @param <T>
     */
    public static <T extends BaseQO> void setPage(com.alijk.bqcommon.base.result.ResponseWrapper respWrapper, T qo,Long total){

        com.alijk.bqcommon.base.pagination.Pagination pagination = new com.alijk.bqcommon.base.pagination.Pagination();
        if (qo.getPageIndex() != null) {
            pagination.setPageIndex(qo.getPageIndex());
        }
        if (qo.getPageSize() != null) {
            pagination.setPageSize(qo.getPageSize());
        }
        if (total != null) {
            pagination.setTotal(total);
        }
        respWrapper.setPage(pagination);


    }
}
