package org.qiranlw.easygo.bean;

import java.util.List;

/**
 * @author qiranlw
 */
public class PageBean<T> {

    /**
     * 当前页数
     */
    private final int pageNum;
    /**
     * 分页大小
     */
    private final int pageSize;
    /**
     * 数据总数
     */
    private final long total;
    /**
     * 当前页数据
     */
    private final List<T> list;

    public PageBean(int pageNum, int pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> PageBean<T> create(int pageNum, int pageSize, long total, List<T> list) {
        return new PageBean<>(pageNum, pageSize, total, list);
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }
}
