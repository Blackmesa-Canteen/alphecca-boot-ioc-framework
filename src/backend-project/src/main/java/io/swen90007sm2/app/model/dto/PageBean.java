package io.swen90007sm2.app.model.dto;

import java.util.List;

/**
 * Page bean, helps wrap data into pages and page queries
 *
 * @author xiaotian
 */
public class PageBean<T> {

    // collection holds all data beans in this page
    private List<T> beans;

    private Integer currentPageNo;
    private Integer pageSize;

    // total pages, calc from total rows / page size
    private Integer totalPageNum;
    private Integer totalRowNum;

    // start row for limit query
    private Integer startRow;

    private PageBean() {
    }

    /**
     * init must follow this format
     * @param pageSize page size
     * @param totalRowNum total row num from database
     * @param currentPageNo target page number
     */
    public PageBean(Integer pageSize, Integer totalRowNum, Integer currentPageNo) {
        this.pageSize = pageSize;
        setTotalRowNum(totalRowNum);
        setCurrentPageNo(currentPageNo);

        // calc start row for limit query
        this.startRow = (currentPageNo - 1) * pageSize;
    }

    public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        this.beans = beans;
    }

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {

        if (currentPageNo == null || currentPageNo < 0) {
            // if input page no is not legal, set 1
            this.currentPageNo = 1;
        } else if (currentPageNo > totalPageNum && totalPageNum > 0) {
            // if page no is too big, set the no as the end
            this.currentPageNo = totalPageNum;
        } else {
            this.currentPageNo = currentPageNo;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public Integer getTotalRowNum() {
        return totalRowNum;
    }

    /**
     * once get total rows, we can calc total pages
     * @param totalRowNum
     */
    public void setTotalRowNum(Integer totalRowNum) {
        this.totalRowNum = totalRowNum;
        totalPageNum = (totalRowNum % pageSize == 0 ? (totalRowNum / pageSize) : (totalRowNum / pageSize + 1));
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }
}
