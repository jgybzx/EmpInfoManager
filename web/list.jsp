<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>员工信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">员工信息列表</h3>

    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>
                <input type="checkbox" id="checkAll">
            </th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>入职日期</th>
            <th>工资</th>
            <th>地址</th>
            <th>操作</th>
        </tr>
        <c:if test="${ not empty pageBeans.empList}">
            <c:forEach var="emp" items="${pageBeans.empList}">
                <tr>
                    <td>
                        <input type="checkbox" name="ids">
                    </td>
                    <td>${emp.id}</td>
                    <td>${emp.ename}</td>
                    <td>${emp.sex}</td>
                    <td>${emp.joindate}</td>
                    <td>${emp.salary}</td>
                    <td>${emp.address}</td>
                    <td><a class="btn btn-default btn-sm"
                           href="${pageContext.request.contextPath}/EmpServlet?action=edit&id=${emp.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm"
                           onclick="deleteEmp(${emp.id})">删除</a></td>
                </tr>
            </c:forEach>
        </c:if>
        <%--
            如果直接删除，会发现页面没有任何提示，万一点错了，还是会删掉。所以要进行一个判断
            希望href属性不执行 可以使用 href='javascript:void(0)'  阻止href属性的继续运行
            href="javascript:void(0)"
        --%>
        <%--href="${pageContext.request.contextPath}/EmpServlet?action=delete&id=${emp.id}">--%>
        <script>
            //希望href属性不执行 可以使用 href='javascript:void(0)'  阻止href属性的继续运行
            //在删除之前进行提示，判断是否真 的要删除
            function deleteEmp(empid) {
                var flag = confirm("是否删除");//点了是就返回 true
                if (flag) {
                    //如果点了是，才继续跳转
                    //如果直接写 href标签，发现因为之前阻止了javascript:void(0)，运行不了，所以可以直接修改浏览器的地址
                    //如果直接修改浏览器的地址，则我们需要当前 用户id，所以可以当作参数传递进来
                    location.href = "${pageContext.request.contextPath}/EmpServlet?action=delete&id=" + empid
                }
            }
        </script>
        <tr>
            <td colspan="9" align="center">
                <a class="btn btn-primary" href="add.jsp">添加员工</a>
                <input class="btn btn-primary" type="button" value="删除选中">
            </td>
        </tr>
        <tr>
            <td colspan="9" align="center">
                <nav>
                    <ul class="pagination">
                        <li >
                            <a href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=1" >
                                <span>首页</span>
                            </a>
                        </li>
                        <%-- 上一页 class="disabled"--%>
<%--                        上一页，那就是当前页减一，刚好pagebean里边有个 pagenumber--%>
                        <li  class="${pageBeans.pagNumber==1?'hidden':''}">
                            <a href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=${pageBeans.pagNumber-1}">

                                <span>&laquo;上一页</span>
                            </a>
                        </li>

                        <%-- 页码显示区 开始循环，开始：1，结束：页码  class="active" 是否激活  判断当前页面是第几个，就激活第几个--%>
                        <c:forEach begin="1" end="${pageBeans.totalPage}" step="1" varStatus="index">
                            <li class="${pageBeans.pagNumber==index.count?'active':''}"><a
                                    href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=${index.count}">${index.count}</a></li>
                        </c:forEach>

                        <%-- 下一页 --%>
                        <li class="${pageBeans.pagNumber==pageBeans.totalPage?'hidden':''}">
                            <a href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=${pageBeans.pagNumber+1}">
                                <span>下一页&raquo;</span>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=${pageBeans.totalPage}" >
                                <span>尾页</span>
                            </a>
                        </li>
                        <span style="font-size:25px;margin-left:5px">
		总${pageBeans.totalCount}条记录，共${pageBeans.totalPage}页
	</span>
                    </ul>

                </nav>
            </td>
        </tr>
    </table>

</div>
</body>
</html>
