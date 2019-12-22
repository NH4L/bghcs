package cn.aysst.bghcs.controller;


import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.entity.Statistics;
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
            return ResultUtils.fail(ResultCode.FAIL, "emailExists");
        else
            return ResultUtils.fail(ResultCode.FAIL, "fail");
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
            return ResultUtils.fail(ResultCode.FAIL, "fail");
    }

    /**
     * 根据专家邮箱查找专家信息
     * @param expertEmail   邮箱
     * @return  专家实体
     */
    @RequestMapping("/getinfo")
    @ResponseBody
    public Result getExpertInfo(@RequestParam String expertEmail) {
        Expert expert = expertService.getExpertInfo(expertEmail);
        if (!(expert==null))
            return ResultUtils.success(expert);
        else
            return ResultUtils.fail(ResultCode.FAIL, "fail");
    }

    /**
     * 修改专家信息（注册邮箱不能修改）
     * @param expert
     * @return
     */
    @RequestMapping("/change")
    @ResponseBody
    public Result changeExpertInfo(@RequestBody Expert expert) {
        String isSuccess = expertService.changeExpertInfo(expert);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else
            return ResultUtils.fail(ResultCode.FAIL, "fail");
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
        Map result = expertService.check(expertEmail, cropName, diseaseName, imageUrl);
        if (result != null)
            return ResultUtils.success(result);
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

    /**
     * 实现统计
     * @return 统计数据
     */
    @RequestMapping("/statistics")
    @ResponseBody
    public String getStatistics(@RequestParam String expertEmail) {
        String result = expertService.getStatistics(expertEmail);
        return result;
    }
}
