package cn.aysst.bghcs.controller;


import cn.aysst.bghcs.entity.User;
import cn.aysst.bghcs.service.UserService;
import cn.aysst.bghcs.util.Result;
import cn.aysst.bghcs.util.ResultCode;
import cn.aysst.bghcs.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
