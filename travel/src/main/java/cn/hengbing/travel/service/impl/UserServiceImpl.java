package cn.hengbing.travel.service.impl;

import cn.hengbing.travel.dao.UserDao;
import cn.hengbing.travel.dao.impl.UserDaoImpl;
import cn.hengbing.travel.domain.ResultInfo;
import cn.hengbing.travel.domain.User;
import cn.hengbing.travel.service.UserService;
import cn.hengbing.travel.util.MailUtils;
import cn.hengbing.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public ResultInfo regist(User user) {
        User u = userDao.findUserByUsername(user.getUsername());
        ResultInfo resultInfo = new ResultInfo();
        if (u != null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名已经存在，注册失败！");
            return resultInfo;
        }
        user.setCode(UuidUtil.getUuid()); // 激活码
        user.setStatus("N"); // 状态
        userDao.save(user);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("注册成功！");

        // 发送激活邮箱地址
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活旅游网</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return resultInfo;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public Boolean active(String code) {
        User user = userDao.findUserByCode(code);
        System.out.println(user);
        if (user == null){
            return false;
        }
        userDao.active(user);
        return true;
    }

    @Override
    public User login(User user) {
        return userDao.findUserBYusernameAndPasswprd(user);
    }
}
