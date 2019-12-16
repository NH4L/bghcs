package cn.aysst.bghcs.controller;


import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.service.ExpertService;
import cn.aysst.bghcs.util.Result;
import cn.aysst.bghcs.util.ResultCode;
import cn.aysst.bghcs.util.ResultUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcy
 * @version 2019-12-12
 * 专家控制层
 */
@RestController
@RequestMapping(value = "/expert", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
public class ExpertController {

    @Autowired
    ExpertService expertService;

    /**
     * 专家注册
     * @param expert
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Result insertExpert(@RequestBody Expert expert) {
        String isSuccess = expertService.insertExpert(expert);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else if (isSuccess.equals("emailExists"))
            return ResultUtils.fail(ResultCode.FAIL, "邮箱已被注册, 请更换邮箱");
        else
            return ResultUtils.fail(ResultCode.FAIL, "服务器繁忙");
    }

    /**
     * 专家登录
     * @param expertEmail
     * @param expertPassword
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestParam String expertEmail, String expertPassword) {
        Expert expert = expertService.login(expertEmail, expertPassword);
        if (!(expert==null))
            return ResultUtils.success(expert);
        else
            return ResultUtils.fail(ResultCode.FAIL, "用户名或密码错误");
    }

    /**
     * 专家检测图片为哪类病虫害
     * @param expertEmail
     * @param cropName
     * @param diseaseName
     * @param imageUrl
     * @return success或fail
     */
    @RequestMapping("/check")
    @ResponseBody
    public Result check(@RequestParam String expertEmail, String cropName, String diseaseName, String imageUrl) {
        String isSuccess = expertService.check(expertEmail, cropName, diseaseName, imageUrl);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else
            return ResultUtils.fail(ResultCode.FAIL, "fail");
    }

    /**
     * 获取已检测图片信息
     * @param expertEmail 专家邮箱
     * @param imageUrl 图片地址
     * @return 图片信息
     */
    @RequestMapping("/get")
    @ResponseBody
    public Result getUserImage(@RequestParam String expertEmail, String imageUrl) {
        Map result = expertService.getCheckedImage(expertEmail, imageUrl);
        if (result != null)
            return ResultUtils.success(result);
        else
            return ResultUtils.fail(ResultCode.FAIL, "null");
    }

    /**
     * 实现分页
     * @param page 当前页数
     * @param pageNum 一页多少条数据
     * @return 图片列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result getAllImage(@RequestParam int page, int pageNum) {
        List<Image> imageList = expertService.getAllImage(page, pageNum);
        return ResultUtils.success(imageList);
    }

}
