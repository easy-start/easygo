package org.qiranlw.easygo.exception;

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

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
