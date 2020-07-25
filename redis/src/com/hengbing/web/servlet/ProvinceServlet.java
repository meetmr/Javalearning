package com.hengbing.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengbing.domain.Province;
import com.hengbing.domain.ResponseAPI;
import com.hengbing.service.impl.ProvinceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json;charset=utf-8"); // 设置响应头
        ProvinceServiceImpl provinceService = new ProvinceServiceImpl();
//        List<Province> provinces = provinceService.FindAll(); // 获取数据
//        ObjectMapper mapper = new ObjectMapper(); // 创建json对象
//        ResponseAPI responseAPI = ResponseAPI.ResponseSuccess(200, "成功", provinces);
//        String json = mapper.writeValueAsString(responseAPI);  // 转换
        String json = provinceService.FindAllJson();
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
