package com.hengbing.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengbing.dao.ProvinceDao;
import com.hengbing.dao.impl.ProvinceDaoImpl;
import com.hengbing.domain.Province;
import com.hengbing.service.ProvinceService;
import com.hengbing.util.JedisPoolUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ProvinceServiceImpl implements ProvinceService {
    ProvinceDaoImpl dao = new ProvinceDaoImpl();
    Jedis jedis = JedisPoolUtil.getJedis();
    @Override
    public List<Province> FindAll() {
       return dao.FindAll();
    }

    @Override
    public String FindAllJson() {
        String province = jedis.get("province");
        if (province == null || province.length() == 0){
            System.out.println("缓存中没有，从数据库中查");
            List<Province> provinces = dao.FindAll();
            ObjectMapper mapper = new ObjectMapper(); // 创建json对象
            String json = "";
            try {
                json = mapper.writeValueAsString(provinces);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.set("province",json);
            return json;
        }
        System.out.println("从缓存中来的");
        // 从缓存中查
        return province;
    }
}
