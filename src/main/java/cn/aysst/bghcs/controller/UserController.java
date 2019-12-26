package cn.aysst.bghcs.controller;


import cn.aysst.bghcs.entity.User;
import cn.aysst.bghcs.service.UserService;
import cn.aysst.bghcs.util.Result;
import cn.aysst.bghcs.util.ResultCode;
import cn.aysst.bghcs.util.ResultUtils;
import cn.aysst.bghcs.util.WXAppletUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lcy
 * @version 2019-12-9
 */
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 插入用户信息
     * @param user 用户实体
     * @return
     */
    @RequestMapping("/insertuser")
    @ResponseBody
    public Result insertUser(@RequestBody  User user) {
        String isSuccess = userService.insertUser(user);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else
            return ResultUtils.fail(ResultCode.FAIL);
    }

    /**
     * 检查用户是否为新用户
     * @param userOpenId
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Result checkOpenId(@RequestParam String userOpenId) {
        String isSuccess = userService.checkOpenId(userOpenId);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else
            return ResultUtils.fail(ResultCode.FAIL);
    }

    @RequestMapping("/getcheckinfo")
    @ResponseBody
    public Result getCheckInfo(@RequestParam String userOpenId, String imageUrl) {
        List<Map> result = userService.getCheckInfo(userOpenId, imageUrl);
        if (result != null)
            return ResultUtils.success(result);
        else
            return ResultUtils.fail(ResultCode.FAIL);
    }


    /**
     * 获取微信小程序 session_key 和 openid
     * @param code 用户
     * @return
     */
    @RequestMapping("/getopenid")
    @ResponseBody
    public String getCheckInfo(@RequestParam String code) {
        String result = WXAppletUserInfo.getSessionKeyOropenid(code);
        return result;
    }

    /**
     *
     * 解密用户敏感数据获取用户信息
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return 用户信息数据
     */
    @RequestMapping("/getuserinfo")
    @ResponseBody
    public String getCheckInfo(@RequestParam String encryptedData, String sessionKey, String iv) {
        String result = WXAppletUserInfo.getUserInfo(encryptedData, sessionKey, iv);
        return result;
    }
}
