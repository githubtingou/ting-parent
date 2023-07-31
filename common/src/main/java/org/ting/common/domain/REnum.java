package org.ting.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返参枚举
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/5
 */
@Getter
@AllArgsConstructor
public enum REnum {
    SUCCESS(200, "请求成功"),
    AUTH_FAIL(403, "权限错误"),
    FAIL(500, "请求失败"),
    PARAM_FAIL(501, "请求参数错误"),
    ;
    private final int code;
    private final String msg;

}
