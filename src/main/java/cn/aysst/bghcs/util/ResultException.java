package cn.aysst.bghcs.util;

/**
 * @author lcy
 * @version 2019-12-9
 * 返回异常类
 */
public class ResultException extends RuntimeException {

    private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}