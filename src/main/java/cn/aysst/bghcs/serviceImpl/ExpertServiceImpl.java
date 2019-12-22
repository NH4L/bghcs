package cn.aysst.bghcs.serviceImpl;

import cn.aysst.bghcs.dao.ExpertDao;
import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.entity.Statistics;
import cn.aysst.bghcs.service.ExpertService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Override
    public Expert login(String expertEmail, String expertPassword) {
        return expertDao.login(expertEmail, expertPassword);
    }

    @Override
    public Map check(String expertEmail, String cropName, String diseaseName, String imageUrl) {
        return expertDao.checkImage(expertEmail, cropName, diseaseName, imageUrl);
    }

    @Override
    public Map getCheckedImage(String expertEmail, String imageUrl) {
        return expertDao.getCheckedImage(expertEmail, imageUrl);
    }

    @Override
    public List<Image> getAllImage(int page, int pageNum) {
        return expertDao.getAllImage(page, pageNum);
    }

    @Override
    public Expert getExpertInfo(String expertEmail) {
        return expertDao.getExpertInfo(expertEmail);
    }

    @Override
    public String changeExpertInfo(Expert expert) {
        return expertDao.changeExpertInfo(expert);
    }

    @Override
    public String getStatistics(String expertEmail) {
        return expertDao.getStatistics(expertEmail);
    }
}
