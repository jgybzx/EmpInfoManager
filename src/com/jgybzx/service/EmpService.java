package com.jgybzx.service;

import com.jgybzx.dao.EmpDao;
import com.jgybzx.domain.Emp;
import com.jgybzx.domain.PageBean;
import com.jgybzx.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author: guojy
 * @date: 2019/12/9 18:07
 * @Description:
 * @version:
 */
public class EmpService {
    public List<Emp> selectAll() {
        List<Emp> emps = null;
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        emps = empDao.selectAll();
        MyBatisUtils.close(sqlSession);
        return emps;
    }

    public Emp fingById(String id) {
        Emp emp = new Emp();
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        emp = empDao.fingById(id);
        MyBatisUtils.close(sqlSession);
        return emp;
    }

    public void update(Emp emp) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        empDao.update(emp);
        MyBatisUtils.close(sqlSession);
    }

    public void delete(String id) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);

        empDao.delete(id);
        MyBatisUtils.close(sqlSession);
    }

    //简单分页查询
    public List<Emp> selectPage(int pageNumber, int pageSize) {
        List<Emp> empList = null;
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        //从前端传递的页码，需要计算处理 一下
        int pageIndex = (pageNumber - 1) * pageSize;
        empList = empDao.selectPage(pageIndex, pageSize);
        MyBatisUtils.close(sqlSession);
        return empList;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return
     */
    //复杂分页查询
    public PageBean<Emp> selectPageBean(int pageNumber, int pageSize) {
//        System.out.println(pageNumber);
//        System.out.println(pageSize);
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        PageBean<Emp> pageBean = new PageBean<>();//创建对象

        //从前端传递的页码，需要计算处理 一下
        int pageIndex = (pageNumber - 1) * pageSize;

        List<Emp> empList = empDao.selectPage(pageIndex, pageSize);
//        System.out.println(empList);
        int  totalCount = empDao.findCount();//从dao取到条数
//        System.out.println("totalCount = " + totalCount);
        int totalPage = (totalCount%pageSize) == 0 ? (totalCount/pageSize):(totalCount/pageSize)+1;
//        System.out.println("totalPage = " + totalPage);
        //赋值对象
        pageBean.setEmpList(empList);//分页 的数据
        pageBean.setTotalCount(totalCount);//总记录数，从dao拿到
        pageBean.setTotalPage(totalPage);//总页数
        pageBean.setPageSize(pageSize);//每页几行  从页面拿到，参数传递
        pageBean.setPagNumber(pageNumber);//第几页 从页面拿到，参数传递

        MyBatisUtils.close(sqlSession);
//        System.out.println("pageBean = " + pageBean);
        return pageBean;//返回对象
    }

    public void insert(Emp emp) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);

        empDao.insert(emp);
        MyBatisUtils.close(sqlSession);
    }

    public void deleteMultiple(String[] ids) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);
        /*Integer[] intIds  = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            intIds[i] = Integer.parseInt(ids[i]);
        }*/
        empDao.deleteMultiple(ids);
        MyBatisUtils.close(sqlSession);
    }
}
