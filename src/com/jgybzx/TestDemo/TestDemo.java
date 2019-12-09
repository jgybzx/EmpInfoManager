package com.jgybzx.TestDemo;

import com.jgybzx.dao.EmpDao;
import com.jgybzx.domain.Emp;
import com.jgybzx.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author: guojy
 * @date: 2019/12/9 17:51
 * @Description:
 * @version:
 */
public class TestDemo {
    @Test
    public void selectAll(){
        SqlSession sqlSession = MyBatisUtils.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        List<Emp> emps = mapper.selectAll();
        for (Emp emp : emps) {
            System.out.println(emp);
        }

    }
}
