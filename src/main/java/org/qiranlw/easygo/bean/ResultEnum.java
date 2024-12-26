package org.qiranlw.easygo.bean;

/**
 * @author qiranlw
 */
public enum ResultEnum {

    /**
     * 返回数据类型枚举数据
     */
    SUCCESS(200, "成功"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有权限"),
    FAILED(500, "失败"),
    FORM_ERROR(510, "表单校验失败");

    /**
     * 返回信息码
     */
    private final int code;
    /**
     * 返回信息
     */
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
