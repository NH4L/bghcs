package cn.aysst.bghcs.controller;

import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.util.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static cn.aysst.bghcs.util.FileGenertor.genericPath;
/**
 * @author lcy
 * @version 2019-12-10
 */
@RestController
@RequestMapping(value = "/image", method = {RequestMethod.GET, RequestMethod.POST})
public class ImageController {

    @RequestMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, String userOpenId, HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        if (file != null) {
            if (file.getSize() > 1024 * 1024 * 10) {
                System.out.println("文件上传失败");
                return ResultUtils.fail(ResultCode.FAIL, "文件过大，请上传10M以内的图片");
            }
            if (file.isEmpty()) {
                return ResultUtils.fail(ResultCode.FAIL, "未选择图片");
            } else {
                try {
                    // 生成文件名
                    String originalFilename = FileGenertor.generateFilename(file);
                    //得出两级目录地址: /3/4
                    String realPath = genericPath(originalFilename, Config.LOCAL_PATH + userOpenId + "/");

                    //磁盘物理地址
                    String diskFileDir = Config.LOCAL_PATH + userOpenId + "/" + realPath + originalFilename;
                    //网络访问地址
                    String networkFileDir = basePath + "/static/images/" + userOpenId + "/"+ realPath + originalFilename;

                    File dest = new File(diskFileDir);
                    file.transferTo(dest);
                    Image image = new Image();
                    image.setImageName(originalFilename);
                    image.setImageAddr(networkFileDir);
                    return ResultUtils.success(image);

                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultUtils.fail(ResultCode.FAIL, "文件上传失败");
                }
            }

        }
        return ResultUtils.fail(ResultCode.FAIL);
    }

}
