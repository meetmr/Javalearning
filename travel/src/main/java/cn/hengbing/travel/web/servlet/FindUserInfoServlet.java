package cn.hengbing.travel.web.servlet;

import cn.hengbing.travel.domain.ResultInfo;
import cn.hengbing.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/findUserInfoServlet")
public class FindUserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json");
        PrintWriter out = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        ResultInfo resultInfo = new ResultInfo();
        User user = (User)req.getSession().getAttribute("user");

        if (user == null){
            resultInfo.setFlag(false);
        }else {
            resultInfo.setFlag(true);
            Map<String,String> map = new HashMap<String, String>();
            map.put("username",user.getUsername());
            resultInfo.setData(map);
        }

        String json = mapper.writeValueAsString(resultInfo);
        out.write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
