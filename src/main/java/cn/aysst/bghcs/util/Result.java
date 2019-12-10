package cn.aysst.bghcs.util;

/**
 * @author lcy
 * @version 2019-12-9
 * @param <T> 泛型T
 * 返回结果类
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result() {

    }

    public Result(ResultCode resultCode, T data) {
        this(resultCode);
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}