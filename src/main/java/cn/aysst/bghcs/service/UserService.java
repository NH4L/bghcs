package cn.aysst.bghcs.service;

import cn.aysst.bghcs.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author lcy
 * @version 2019-12-9
 */
@Service
public interface UserService {

    String insertUser(User user);

    String checkOpenId(String userOpenId);
}
