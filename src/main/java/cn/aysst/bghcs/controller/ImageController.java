package cn.aysst.bghcs.controller;

import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.util.Result;
import cn.aysst.bghcs.util.ResultCode;
import cn.aysst.bghcs.util.ResultUtils;
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

/**
 * @author lcy
 * @version 2019-12-10
 */
@RestController
@RequestMapping(value = "/image", method = {RequestMethod.GET, RequestMethod.POST})
public class ImageController {

    @RequestMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) {

        if (file.getSize() > 1024 * 1024 * 10) {
            System.out.println("文件上传失败");
            return ResultUtils.fail(ResultCode.FAIL, "文件过大，请上传10M以内的图片");
        }
        String path = request.getContextPath();
        System.out.println("path:" + path);
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        System.out.println("basePath:" + basePath);
        Date dt = new Date();
        Long time = dt.getTime();
        if (file != null) {
            String realPath = "e://HHU/ThirdYear/images/";// 获取保存的路径，本地磁盘中的一个文件夹
//            String realPath = "/usr/projects/bghcs/";// 获取保存的路径，本地磁盘中的一个文件夹
            if (file.isEmpty()) {
                // 未选择文件
                return ResultUtils.fail(ResultCode.FAIL, "未选择图片");
            } else {
                // 文件原名称
                String originFileName = "";
                // 上传文件重命名
                String originalFilename = time.toString().substring(time.toString().length() - 8,
                        time.toString().length());
                originalFilename = originalFilename.concat(".");
                originalFilename = originalFilename.concat(file.getOriginalFilename().toString()
                        .substring(file.getOriginalFilename().toString().indexOf(".") + 1));
                try {
                    File dest = new File(realPath + originalFilename);
                    System.out.println(realPath+originalFilename);
                    file.transferTo(dest);
                    Image image = new Image();
                    image.setImageName(originalFilename);
                    image.setImageAddr(basePath + "/static/image/" + originalFilename);
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
