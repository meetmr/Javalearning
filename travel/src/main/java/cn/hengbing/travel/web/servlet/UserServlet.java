package cn.hengbing.travel.web.servlet;

import cn.hengbing.travel.domain.ResultInfo;
import cn.hengbing.travel.domain.User;
import cn.hengbing.travel.service.UserService;
import cn.hengbing.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private final UserService service = new UserServiceImpl();

    /**
     * 注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8"); // 设置相应编码
        ResultInfo resultInfo = new ResultInfo(); // 返回
        resp.setContentType("text/json"); // 设置返回文本类型为json
        PrintWriter out = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper(); // 获取json封装对象

        // 1、验证验证码是否正确
        String checkcode_server = (String)req.getSession().getAttribute("CHECKCODE_SERVER"); // 服务端生成的验证码
        String check = req.getParameter("check");

        // 2、校验验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            String json = mapper.writeValueAsString(resultInfo);
            out.write(json);
            return ;
        }

        // 3、获取前端传来的数据
        Map<String, String[]> map = req.getParameterMap();

        // 4、封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);

        // 5、调用service完成注册

        ResultInfo flag = service.regist(user);

        if (!flag.isFlag()){ // 失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg(flag.getErrorMsg());
            String json = mapper.writeValueAsString(resultInfo);
            out.write(json);
            return;
        }

        resultInfo.setFlag(true);
        resultInfo.setErrorMsg(flag.getErrorMsg());
        String json = mapper.writeValueAsString(resultInfo);
        out.write(json);
    }

    /**
     * 登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json");
        PrintWriter out = resp.getWriter();
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();

        // 1、校验验证码
        String checkcode_server = (String)req.getSession().getAttribute("CHECKCODE_SERVER");
        String code = req.getParameter("check");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(code)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            String json = mapper.writeValueAsString(resultInfo);
            out.write(json);
            return ;
        }

        // 2、获取数据
        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 2、处理登录

        User u = service.login(user);


        if (u == null){ // 没有查询到数据
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或秘密错误！");

        }
        if ( u != null && !"Y".equals(u.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户没有激活，请激活！");
        }
        if (u != null && "Y".equals(u.getStatus())){
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("登录成功");
        }

        req.getSession().setAttribute("user",u);

        String json = mapper.writeValueAsString(resultInfo);
        out.write(json);
    }

    /**
     * 退出登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void outLoin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }

    /**
     * 激活
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 1、获取激活码
        String code = req.getParameter("code");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        Boolean tag = service.active(code);
        String msg = "";
        if (!tag){
            msg = "激活失败";
        }else {
            msg = "<a href='http://localhost/travel/login.html'>激活成功，点击登录</a>";
        }
        resp.getWriter().write(msg);
    }


    public void findUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
