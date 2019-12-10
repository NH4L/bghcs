package cn.aysst.bghcs.serviceImpl;

import cn.aysst.bghcs.dao.UserDao;
import cn.aysst.bghcs.entity.User;
import cn.aysst.bghcs.service.UserService;
import cn.aysst.bghcs.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lcy
 * @version 2019-12-9
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public String insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public String checkOpenId(String userOpenId) {
        return userDao.checkOpenId(userOpenId);
    }
}
