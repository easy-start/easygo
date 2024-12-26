package org.qiranlw.easygo.exception;

import java.util.Map;

/**
 * @author qiranlw
 */
public class MethodArgumentNotValidException extends ServiceException {

    private final Map<String, String> errors;

    public MethodArgumentNotValidException(Map<String, String> errors) {
        super(510, "表单校验失败");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
