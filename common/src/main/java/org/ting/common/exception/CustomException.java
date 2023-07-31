package org.ting.common.exception;

import lombok.Getter;
import org.ting.common.domain.REnum;

/**
 * 自定义异常
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/5
 */
@Getter
public class CustomException extends RuntimeException {

    private Integer code = REnum.FAIL.getCode();
    private String msg = REnum.FAIL.getMsg();

    public CustomException() {
        super();
    }

    public CustomException(Integer code, String msg) {
        super(code + "-" + msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomException(REnum rEnum) {
        super(rEnum.getCode() + "-" + rEnum.getMsg());
        this.code = rEnum.getCode();
        this.msg = rEnum.getMsg();
    }
}
