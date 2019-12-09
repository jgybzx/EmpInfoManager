package com.jgybzx.dao;

import com.jgybzx.domain.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: guojy
 * @date: 2019/12/9 17:52
 * @Description:
 * @version:
 */
public interface EmpDao {
    @Select("select * from emp")
    public List<Emp> selectAll();

    @Select("select * from emp where id = #{id}")
    public Emp fingById(String id);

    @Update("update emp set ename=#{ename},sex=#{sex},joindate=#{joindate},salary=#{salary},address=#{address} where id = #{id}")
    public void update(Emp emp);

    @Delete("delete from emp where id = #{id}")
    public void delete(String id);
}
