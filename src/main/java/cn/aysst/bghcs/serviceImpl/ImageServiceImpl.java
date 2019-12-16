package cn.aysst.bghcs.serviceImpl;

import cn.aysst.bghcs.dao.ImageDao;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lcy
 * @version 2019-12-11
 */
@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDao imageDao;

    @Override
    public String insertImage(List<Image> list, String userOpenId) {
        return imageDao.insertImage(list, userOpenId);
    }

    @Override
    public List<Image> getUserImage(String userOpenId) {
        return imageDao.getUserImage(userOpenId);
    }

    @Override
    public int getImageNum() {
        return imageDao.getImageNum();
    }
}
