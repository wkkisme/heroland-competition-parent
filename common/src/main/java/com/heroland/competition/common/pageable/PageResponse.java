package com.heroland.competition.common.pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页出参对象
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -86796266611673861L;

    /**
     * 获取页码,页编码从1开始
     */
    private int page;
    private int pageSize;
    private int total;
    private List<T> items;

    public int getTotalPages() {
        return this.pageSize == 0 ? 1 : (int) Math.ceil((double) this.total / (double) this.pageSize);
    }

    public boolean isFirstPage() {
        return this.page <= 1;
    }

    public boolean hasNextPage() {
        return this.page < this.getTotalPages();
    }


    public boolean isLastPage() {
        return !this.hasNextPage();
    }
}
