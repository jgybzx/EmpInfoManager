package com.jgybzx.web.servlet;

import com.jgybzx.domain.Emp;
import com.jgybzx.service.EmpService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2019/12/9 18:05
 * @Description: ${TODO}
 * @version:
 */
//http://localhost:8080/
@WebServlet("/EmpServlet")
//WebServlet(name = "EmpServlet",urlPatterns="/EmpServlet")
public class EmpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //每次执行一个操作，要写 一个servlet，为了提高效率，将所有的servlet操作，放在一个servlet中
        //根据 每次跳转的操作，得到一个action  ，针对不同的action，执行不同的方法
        //action="findAll"  action = "edit" action="update" action="save" action = delete
        String action = request.getParameter("action");
        if("findAll".equals(action)){//查询全部
            this.findAll(request,response);
        }else if ("edit".equals(action)){//修改之前需要查询
            this.edit(request,response);
        }else if ("update".equals(action)){//修改
            this.update(request,response);
        }else if ("save".equals(action)){//保存

        }else if ("delete".equals(action)){//删除
            this.delete(request,response);
        }
    }
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpService empService = new EmpService();
        List<Emp> empList =  empService.selectAll();
        request.setAttribute("empList",empList);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        //请求转发到
        request.getRequestDispatcher("list.jsp").forward(request,response);
    }
    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //修改之前，根据id，查询出一条数据,所以需要前台页面传递一个id
        String id = request.getParameter("id");
        System.err.println("id="+id);
        EmpService empService = new EmpService();
        Emp emp = empService.fingById(id);
        request.setAttribute("emp",emp);
        //获取到数据之后，请求转发到显示页面，在显示页面改显示的显示
        request.getRequestDispatcher("update.jsp").forward(request,response);
    }
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.printf("执行update");
        try {
            //接受传递的参数，封装成一个对象，然后 写入数据库
            Emp emp = new Emp();
            Map<String, String[]> parameterMap = request.getParameterMap();
            System.out.println(parameterMap);
            BeanUtils.populate(emp,parameterMap);
            //封装成对象之后，调用service 然后调用dao进行数据存储
            EmpService empService = new EmpService();
            empService.update(emp);
            //数据存完之后，重定向到查询页面，不要忘记 传递action参数
            response.sendRedirect(request.getContextPath()+"/EmpServlet?action=findAll");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递的要删除的id
        String id = request.getParameter("id");
        EmpService empService = new EmpService();
        empService.delete(id);

        //数据删完，重定向到查询页面，不要忘记 传递action参数
        response.sendRedirect(request.getContextPath()+"/EmpServlet?action=findAll");
    }
}
