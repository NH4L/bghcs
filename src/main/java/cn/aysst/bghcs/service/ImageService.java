package cn.aysst.bghcs.service;

import cn.aysst.bghcs.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcy
 * @version 2019-12-11
 */
@Service
public interface ImageService {

    String insertImage(List<Image> list, String userOpenId);

    List<Image> getUserImage(String userOpenId);
}
