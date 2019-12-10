package cn.aysst.bghcs.util;

/**
 * @author lcy
 * @version 2019-12-9
 * 返回结果工具类
 */
public class ResultUtils {

    public static Result success(Object data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static Result success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static Result fail(ResultCode resultCode, String msg) {
        Result<Object> result = new Result<>(resultCode);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(ResultCode resultCode) {
        return new Result(resultCode);
    }

}