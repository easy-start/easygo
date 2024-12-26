package org.qiranlw.easygo.form;

/**
 * @author qiranlw
 */
public class PageForm {

    public static final int MAX_PAGE_SIZE = 1000;

    public static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize;

    private int pageNum;

    public PageForm() {
        this.pageNum = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum < 1) {
            this.pageSize = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
    }

    public int getStartNum() {
        return (this.pageNum-1)*this.pageSize;
    }
}
