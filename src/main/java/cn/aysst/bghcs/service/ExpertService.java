package cn.aysst.bghcs.service;

import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.entity.Statistics;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lcy
 * @version 2019-12-12
 * 专家服务层
 */
@Service
public interface ExpertService {

    String insertExpert(Expert expert);

    Expert login(String expertEmail, String expertPassword);

    Map check(String expertEmail, String cropName, String diseaseName, String imageUrl);

    Map getCheckedImage(String expertEmail, String imageUrl);

    List<Image> getAllImage(int page, int pageNum);

    Expert getExpertInfo(String expertEmail);

    String changeExpertInfo(Expert expert);

    String getStatistics(String expertEmail);
}
