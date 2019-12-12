package com.jgybzx.web.servlet;

import com.jgybzx.domain.Emp;
import com.jgybzx.domain.PageBean;
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
import java.util.Set;

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
        if ("findAll".equals(action)) {//查询全部
//            this.findAll(request,response);
//            this.findAllPage(request, response);//分页查询
            this.findAllPageBean(request, response);//复杂分页查询
        } else if ("edit".equals(action)) {//修改之前需要查询
            this.edit(request, response);
        } else if ("update".equals(action)) {//修改
            this.update(request, response);
        } else if ("save".equals(action)) {//保存
            this.save(request, response);
        } else if ("delete".equals(action)) {//删除
            this.delete(request, response);
        } else if ("deleteMultiple".equals(action)) {
            this.deleteMultiple(request, response);
        }
    }

    /**
     * 保存数据，取出页所有元素Map。封装到类中，调用方法传递对象进行保存
     * 数据添加结束，重定向到查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EmpService empService = new EmpService();
            Map<String, String[]> parameterMap = request.getParameterMap();
//            int pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
            String pageNumber = request.getParameter("pageNumber");
            System.out.println("pageNumber = " + pageNumber);
            Emp emp = new Emp();
            BeanUtils.populate(emp, parameterMap);

            empService.insert(emp);
            ///数据添加，重定向到查询页面，不要忘记 传递action参数
            response.sendRedirect(request.getContextPath() + "/EmpServlet?action=findAll&pageNumber=1");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpService empService = new EmpService();
        List<Emp> empList = empService.selectAll();
        request.setAttribute("empList", empList);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        //请求转发到
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    public void findAllPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 分页查询
        /**
         * 分页显示，自然要从数据库里边每次都是分页的查
         * #第n页的 数据
         * SELECT * FROM emp LIMIT (pageNumber-1)*pageSize , pageSize;
         * 所以需要前台给我传递一个 pageNumber 和 pageSize;  才能具体的查询。
         * 目前，
         *
         * 所以我们启动查询页面的时候，都要先传递一个pageNumber 和pageSize;
         *         //1.获得请求数据
         *        String pageNumberStr = request.getParameter("pageNumber");（暂时在请求中手动写）
         *        String pageSizeStr = request.getParameter("pageSize");（暂时写死）
         */
        //获取数据
        String pageNumberStr = request.getParameter("pageNumber");//（暂时在请求中手动写）
        String pageSizeStr = request.getParameter("pageSize");//（暂时写死）
        int pageSize = 5;
        //页码
        int pageNumber = Integer.parseInt(pageNumberStr);
        //处理请求数据，每次不在是全部查询，而是分页查询
        EmpService empService = new EmpService();
        List<Emp> empList = empService.selectPage(pageNumber, pageSize);
        request.setAttribute("empList", empList);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        //请求转发到
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    public void findAllPageBean(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 复杂分页查询
        //获取数据
        String pageNumberStr = request.getParameter("pageNumber");//（   ）
//        System.out.println(pageNumberStr);
        String pageSizeStr = request.getParameter("pageSize");//（暂时写死）
        int pageSize = 5;
        //页码,这个 由于get提交，这个地方的页码可以人为修改，
        // 从而就会造成类型转换异常，所以需要来个异常处理
        int pageNumber = 1;//先定义一个1，如果转换异常，那么
        try {//如果类型转换异常，那么pageNumber还是1
            pageNumber = Integer.parseInt(pageNumberStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //处理请求数据，每次不在是全部查询，而是分页查询
        EmpService empService = new EmpService();
        //将获取到的pageBean返回，里边已经包含了很多数据
        PageBean<Emp> pageBeans = empService.selectPageBean(pageNumber, pageSize);
//        System.out.println(pageBeans);
        request.setAttribute("pageBeans", pageBeans);

        //请求转发到
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //修改之前，根据id，查询出一条数据,所以需要前台页面传递一个id
        String id = request.getParameter("id");
        System.err.println("id=" + id);
        EmpService empService = new EmpService();
        Emp emp = empService.fingById(id);
        request.setAttribute("emp", emp);
        //获取到数据之后，请求转发到显示页面，在显示页面改显示的显示
        request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.printf("执行update");
        try {
            //接受传递的参数，封装成一个对象，然后 写入数据库
            Emp emp = new Emp();
            Map<String, String[]> parameterMap = request.getParameterMap();
            System.out.println(parameterMap);
            BeanUtils.populate(emp, parameterMap);
            //封装成对象之后，调用service 然后调用dao进行数据存储
            EmpService empService = new EmpService();
            empService.update(emp);
            //数据存完之后，重定向到查询页面，不要忘记 传递action参数，以及pageNumber
            response.sendRedirect(request.getContextPath() + "/EmpServlet?action=findAll&pageNumber=1");
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
        response.sendRedirect(request.getContextPath() + "/EmpServlet?action=findAll&pageNumber=1");
    }

    public void deleteMultiple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递的要删除的id  集合
        String[] ids ;
        ids = request.getParameterValues("ids");
        System.out.println("ids = " + ids);
        EmpService empService = new EmpService();
        empService.deleteMultiple(ids);

        //数据删完，重定向到查询页面，不要忘记 传递action参数
        response.sendRedirect(request.getContextPath() + "/EmpServlet?action=findAll&pageNumber=1");
    }
}
