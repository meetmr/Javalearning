package cn.hengbing.travel.web.servlet;

import cn.hengbing.travel.service.UserService;
import cn.hengbing.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // 1、获取激活码
       String code = req.getParameter("code");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        UserService service = new UserServiceImpl();
        Boolean tag = service.active(code);
        String msg = "";
        if (!tag){
            msg = "激活失败";
        }else {
            msg = "<a href='http://localhost/travel/login.html'>激活成功，点击登录</a>";
        }
        resp.getWriter().write(msg);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
