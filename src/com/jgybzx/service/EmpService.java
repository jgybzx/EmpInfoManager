package com.jgybzx.service;

import com.jgybzx.dao.EmpDao;
import com.jgybzx.domain.Emp;
import com.jgybzx.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

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
        emp  = empDao.fingById(id);
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

    public List<Emp> selectPage(int pageNumber, int pageSize) {
        List<Emp> empList = null;
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);

        int pageIndex = (pageNumber-1)*pageSize;
        empList = empDao.selectPage(pageIndex,pageSize);
        MyBatisUtils.close(sqlSession);
        return empList;

    }

    public void insert(Emp emp) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao empDao = sqlSession.getMapper(EmpDao.class);

        empDao.insert(emp);
        MyBatisUtils.close(sqlSession);
    }
}
