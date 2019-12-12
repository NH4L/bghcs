package cn.aysst.bghcs.serviceImpl;

import cn.aysst.bghcs.dao.ExpertDao;
import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lcy
 * @version 2019-12-12
 */
@Component
public class ExpertServiceImpl implements ExpertService {

    @Autowired
    ExpertDao expertDao;

    @Override
    public String insertExpert(Expert expert) {
        return expertDao.insertExpert(expert);
    }
}
