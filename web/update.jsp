<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>修改员工</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <center><h3>修改员工页面</h3></center>
    <form action="EmpServlet?action=update" method="post">
        <input name="id" value="${emp.id}" hidden>
        <div class="form-group">
            <label for="ename">姓名：</label>
            <input type="text" class="form-control" id="ename" name="ename" value="${emp.ename}" placeholder="请输入姓名">
        </div>

        <div class="form-group">
            <label>性别：</label>
            <%--性别来个三元判断，如果当前参数的男，就设置属性checked--%>

            <input type="radio" name="sex" value="男" ${emp.sex =="男"?'checked':''}/>男
            <input type="radio" name="sex" value="女" ${emp.sex =="女"?'checked':''}/>女
        </div>

        <div class="form-group">
            <label for="joindate">入职日期：</label>
            <input type="date" class="form-control" id="joindate" name="joindate" value="${emp.joindate}">
        </div>
        <div class="form-group">
            <label for="salary">工资：</label>
            <input type="text" class="form-control" id="salary" name="salary" placeholder="请输入工资" value="${emp.salary}">
        </div>

        <div class="form-group">
            <label for="address">地址：</label>
            <select name="address" class="form-control" id="address">
                <option value="">--请选择--</option>
                <option value="北京" ${emp.address == "北京"?'selected':''}>北京</option>
                <option value="上海" ${emp.address == "上海"?'selected':''}>上海</option>
                <option value="广州" ${emp.address == "广州"?'selected':''}>广州</option>
                <option value="深圳" ${emp.address == "深圳"?'selected':''}>深圳</option>
            </select>
        </div>
        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交"/>
            <input class="btn btn-default" type="reset" value="重置"/>
            <input class="btn btn-default" type="button" value="返回" onclick="history.go(-1)"/>
        </div>
    </form>
</div>
</body>
</html>