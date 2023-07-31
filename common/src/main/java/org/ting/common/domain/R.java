package org.ting.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返参模板类
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 成功返参
     *
     * @param data 返参数据
     * @param <T>  泛型
     * @return {@link R}
     */
    public static <T> R<T> success(T data) {
        return new R<>(REnum.SUCCESS.getCode(), REnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返参,无data数据
     *
     * @return {@link R}
     */
    public static R<Void> success() {
        return success(null);
    }

    /**
     * 失败返参
     *
     * @param <T> 泛型
     * @return {@link R}
     */
    public static <T> R<T> fail() {
        return new R<>(REnum.FAIL.getCode(), REnum.FAIL.getMsg());
    }

    /**
     * 失败返参
     *
     * @param code code
     * @param msg  msg
     * @return {@link R}
     */
    public static <T> R<T> fail(Integer code, String msg) {
        return new R<>(code, msg);
    }

    /**
     * 根据枚举封装返参
     *
     * @param rEnum {@link REnum}
     * @param <T>   泛型
     * @return {@link R}
     */
    public static R<Void> fail(REnum rEnum) {
        return new R<>(rEnum.getCode(), rEnum.getMsg());
    }

}
