package org.ting.minio.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/19
 */
public class UUIDUtils {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
}
