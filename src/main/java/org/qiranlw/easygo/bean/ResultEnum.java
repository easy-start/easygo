package org.qiranlw.easygo.bean;

/**
 * @author qiranlw
 */
public enum ResultEnum {

    /**
     * 返回数据类型枚举数据
     */
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "错误请求"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "方法禁用"),
    NOT_ACCEPTABLE(406, "不可接受"),
    PROXY_AUTHORIZATION(407, "需要代理授权"),
    REQUEST_TIMEOUT(408, "请求超时"),
    SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "尚未实现"),
    BAD_GATEWAY(502, "错误网关"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP 版本不受支持"),
    FORM_ERROR(510, "表单校验失败"),
    DATA_NOT_FOUND(511, "数据未找到"),
    DATA_SAVE_FAILED(512, "保存数据失败"),
    DATA_UPDATE_FAILED(513, "数据更新失败"),
    DATA_DELETE_FAILED(514, "数据删除失败"),
    UNPARSEABLE_RESPONSE_HEADERS(600, "没有返回响应头部");

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
