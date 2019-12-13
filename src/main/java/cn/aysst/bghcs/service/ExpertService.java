package cn.aysst.bghcs.service;

import cn.aysst.bghcs.entity.Expert;
import org.springframework.stereotype.Service;

/**
 * @author lcy
 * @version 2019-12-12
 * 专家服务层
 */
@Service
public interface ExpertService {

    String insertExpert(Expert expert);

    Expert login(String expertEmail, String expertPassword);
}
