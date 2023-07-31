package org.ting.common.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/4
 */
public class UUIDUtils {
    public static String getId(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.getId());
    }
}
