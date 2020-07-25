package com.hengbing.dao.impl;

import com.hengbing.dao.ProvinceDao;
import com.hengbing.domain.Province;
import com.hengbing.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProvinceDaoImpl implements ProvinceDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Province> FindAll() {
        String sql = "select * from province";
        System.out.println(sql);
        List<Province> list = template.query(sql, new BeanPropertyRowMapper<Province>(Province.class));
        return list;
    }
}
