package cn.hengbing.travel.dao.impl;

import cn.hengbing.travel.dao.UserDao;
import cn.hengbing.travel.domain.User;
import cn.hengbing.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
             user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    /**
     * 用户注册入库
     * @param user
     */
    @Override
    public void save(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        template.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (Exception e){

        }
        return user;
    }

    /**
     * 激活用户
     * @param user
     */
    @Override
    public void active(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ? ";
        template.update(sql,user.getUid());
    }

    /**
     * 处理登录
     * @param user
     * @return
     */
    @Override
    public User findUserBYusernameAndPasswprd(User user) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User u = null;
        try {
            u = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        }catch (Exception e){
        }
        return u;
    }
}
