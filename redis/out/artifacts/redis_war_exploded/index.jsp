<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/24
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="js/jquery-3.3.1.js"></script>
    <script>
       $(function () {
          $.get("provinceServlet",{},function (data) {
              if (data.code == 200){
                console.log(data.data)
                for (var i = 1;i < data.data.length;i++){
                     var html = "<option value="+data.data[i]["id"]+">"+data.data[i]['name']+"</option>";
                     $("#pro").append(html);
                }
              }
          });
       })
    </script>
  </head>
  <body>
    <select id="pro">
      <option>--请选择--</option>
    </select>
  </body>
</html>
