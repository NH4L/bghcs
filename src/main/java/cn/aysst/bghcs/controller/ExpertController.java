package cn.aysst.bghcs.controller;


import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.service.ExpertService;
import cn.aysst.bghcs.util.Result;
import cn.aysst.bghcs.util.ResultCode;
import cn.aysst.bghcs.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/insertexpert")
    @ResponseBody
    public Result insertExpert(@RequestBody Expert expert) {
        String isSuccess = expertService.insertExpert(expert);
        if (isSuccess.equals("success"))
            return ResultUtils.success();
        else
            return ResultUtils.fail(ResultCode.FAIL);
    }

}
