package com.hengbing.service;

import com.hengbing.domain.Province;

import java.util.List;

public interface ProvinceService {
    public List<Province> FindAll();

    public String FindAllJson();

}
