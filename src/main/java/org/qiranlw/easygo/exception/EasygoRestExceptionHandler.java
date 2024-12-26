package org.qiranlw.easygo.exception;

import org.qiranlw.easygo.bean.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 统一处理系统抛出的各种异常
 * @author qiranlw
 */
@RestControllerAdvice
public class EasygoRestExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result<String> bindServiceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, String>> bindMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.error(e.getCode(), e.getMessage(), e.getErrors());
    }

    @ExceptionHandler(Exception.class)
    public Result<Exception> bindException(Exception e) {
        return Result.error(e.getMessage(), e);
    }
}
