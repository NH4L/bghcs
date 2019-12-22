package cn.aysst.bghcs.service;

import cn.aysst.bghcs.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lcy
 * @version 2019-12-9
 */
@Service
public interface UserService {

    String insertUser(User user);

    String checkOpenId(String userOpenId);

    List<Map> getCheckInfo(String userOpenId, String imageUrl);
}
