package org.qiranlw.easygo.exception;

import org.qiranlw.easygo.bean.ResultEnum;

import java.util.Map;

/**
 * @author qiranlw
 */
public class MethodArgumentNotValidException extends ServiceException {

    private final Map<String, String> errors;

    public MethodArgumentNotValidException(Map<String, String> errors) {
        super(ResultEnum.FORM_ERROR);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
