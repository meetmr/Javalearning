package cn.hengbing.travel.service;

import cn.hengbing.travel.domain.ResultInfo;
import cn.hengbing.travel.domain.User;

public interface UserService {
    public ResultInfo regist(User user);

    public Boolean active(String code);

    public  User login(User user);
}
