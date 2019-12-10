package com.jgybzx.domain;

import java.util.List;

/**
 * @author: guojy
 * @date: 2019/12/10 14:56
 * @Description: 页面数据传递类，涵盖这分页 所有的数据
 * @version:
 * 总记录数  totalCount
 * 总页码     totalPage
 * list数据   list<emp>
 * 当前页码  pagNumber
 * pageSize
 *
 */
public class PageBean<T> {
    public PageBean() {
    }

    //  emp数据
    private List<T> empList;
    private Integer totalCount;
    private Integer totalPage;
    private Integer pagNumber;
    private Integer pageSize;//目前传递的时候，默认写死

    public List<T> getEmpList() {
        return empList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "empList=" + empList +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", pagNumber=" + pagNumber +
                ", pageSize=" + pageSize +
                '}';
    }

    public void setEmpList(List<T> empList) {
        this.empList = empList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPagNumber() {
        return pagNumber;
    }

    public void setPagNumber(Integer pagNumber) {
        this.pagNumber = pagNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageBean(List<T> empList, Integer totalCount, Integer totalPage, Integer pagNumber, Integer pageSize) {
        this.empList = empList;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pagNumber = pagNumber;
        this.pageSize = pageSize;
    }
}
