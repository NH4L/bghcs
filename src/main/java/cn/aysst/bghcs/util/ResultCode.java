package cn.aysst.bghcs.util;

/**
 * @author lcy
 * @version 2019-12-9
 * 枚举类
 */
public enum  ResultCode {

    SUCCESS(0, "success"),
    FAIL(-1, "fail");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}