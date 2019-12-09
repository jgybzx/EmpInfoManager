package com.jgybzx.domain;

/**
 * @author: guojy
 * @date: 2019/12/9 17:46
 * @Description:
 * @version:
 */

/**
 * id           int(11)
 * ename        varchar(150)
 * sex          varchar(32)
 * joindate     date
 * salary       decimal(9,0)
 * address      varchar(32)
 */
public class Emp {
    public Emp() {
    }

    private Integer id;
    private String ename;
    private String sex;
    private String joindate;
    private Double salary;
    private String address;

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", sex='" + sex + '\'' +
                ", joindate='" + joindate + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Emp(Integer id, String ename, String sex, String joindate, Double salary, String address) {
        this.id = id;
        this.ename = ename;
        this.sex = sex;
        this.joindate = joindate;
        this.salary = salary;
        this.address = address;
    }
}
