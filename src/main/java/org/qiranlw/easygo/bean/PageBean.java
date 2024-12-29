package org.qiranlw.easygo.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @param pageNum  当前页数
 * @param pageSize 分页大小
 * @param total    数据总数
 * @param list     当前页数据
 * @author qiranlw
 */
public record PageBean<T>(int pageNum, int pageSize, long total, List<T> list) implements Serializable {

    @Serial
    private static final long serialVersionUID = 6986935208333748699L;

    public static <T> PageBean<T> create(int pageNum, int pageSize, long total, List<T> list) {
        return new PageBean<>(pageNum, pageSize, total, list);
    }
}
