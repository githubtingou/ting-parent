package org.ting.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ting.common.domain.R;
import org.ting.common.domain.REnum;

/**
 * 全局异常处理
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/5
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public R<Void> throwableHandler(Throwable e) {
        log.error("全局异常处理,", e);
        return R.fail();
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    public R<Void> customExceptionHandler(CustomException e) {
        log.error("CustomException异常处理,", e);
        return R.fail(e.getCode(), e.getMsg());
    }

    /**
     * validation异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Void> validExceptionHandler(MethodArgumentNotValidException e) {
        log.error("validException异常处理,", e);
        return R.fail(REnum.PARAM_FAIL.getCode(), e.getMessage());

    }
}
