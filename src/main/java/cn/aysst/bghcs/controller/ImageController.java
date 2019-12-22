package cn.aysst.bghcs.controller;

import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.service.ImageService;
import cn.aysst.bghcs.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static cn.aysst.bghcs.util.FileGenertor.genericPath;
/**
 * @author lcy
 * @version 2019-12-10
 */
@RestController
@RequestMapping(value = "/image", method = {RequestMethod.GET, RequestMethod.POST})
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * 微信用户上传图片
     * @param files 图片数组
     * @param userOpenId 用户userOpenId
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam MultipartFile[] files, String userOpenId) {
//        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String basePath = Config.BASE_PATH;
        System.out.println(basePath);
        List<Image> list = new ArrayList<Image>();
        for (MultipartFile file: files) {
            try {
                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 10) {
                        System.out.println("文件上传失败");
                        return ResultUtils.fail(ResultCode.FAIL, "文件过大，请上传10M以内的图片");
                    }
                    if (file.isEmpty()) {
                        System.out.println("文件上传失败");
                        return ResultUtils.fail(ResultCode.FAIL, "未选择图片");
                    }

                }
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
                System.out.println("上传文件成功");
                Image image = new Image();
                image.setImageName(originalFilename);
                image.setImageUrl(networkFileDir);
                list.add(image);

            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtils.fail(ResultCode.FAIL, "文件上传失败");
            }
        }
        if (list.isEmpty())
            return ResultUtils.fail(ResultCode.FAIL, "未选择图片");
        else {
            String isSuccess = imageService.insertImage(list, userOpenId);
            if (isSuccess.equals("success"))
                return ResultUtils.success(list);
            else
                return ResultUtils.fail(ResultCode.FAIL, "数据库访问繁忙");
        }

    }

    /**
     * 根据userOpenId获取用户所上传的图片
     * @param userOpenId
     * @return 图片列表
     */
    @RequestMapping("/get")
    @ResponseBody
    public Result getUserImage(@RequestParam String userOpenId) {
        List<Image> imageList = imageService.getUserImage(userOpenId);
        return ResultUtils.success(imageList);
    }

    /**
     * 获取图片一共多少张，方便后续进行分页
     * @return
     */
    @RequestMapping("/getimagenum")
    @ResponseBody
    public Result getImageNum() {
        int imageNum = imageService.getImageNum();
        if (imageNum > -1) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("imageNum", imageNum);
            return ResultUtils.success(map);
        } else
            return ResultUtils.fail(ResultCode.FAIL, "fail");
    }

}
