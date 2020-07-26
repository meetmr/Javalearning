package cn.hengbing.travel.dao;

import cn.hengbing.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 用户入库
     */
    public void save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    public User findUserByCode(String code);

    /**
     * 激活用户
     * @param user
     */
    public  void active(User user);

    public User findUserBYusernameAndPasswprd(User user);
}
