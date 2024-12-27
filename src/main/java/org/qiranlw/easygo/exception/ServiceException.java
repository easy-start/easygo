package org.qiranlw.easygo.exception;

import org.qiranlw.easygo.bean.ResultEnum;

/**
 * @author qiranlw
 */
public class ServiceException extends RuntimeException {

    private final int code;

    private final String message;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
