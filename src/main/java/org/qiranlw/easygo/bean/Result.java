package org.qiranlw.easygo.bean;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用于向前端返回数据
 * @author qiranlw
 * @param <T>
 */
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7237600264469877352L;

    private final int code;
    private final String message;
    private final T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultEnum result, T data) {
        this.code = result.getCode();
        this.message = result.getMessage();
        this.data = data;
    }

    public Result(T data) {
        this(ResultEnum.SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(ResultEnum result) {
        return new Result<>(result.getCode(), result.getMessage(), null);
    }

    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ResultEnum.SERVER_ERROR.getCode(), message, null);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(ResultEnum.SERVER_ERROR.getCode(), message, data);
    }

    public static <T> Result<T> forbidden() {
        return new Result<>(ResultEnum.FORBIDDEN, null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
