package org.ting.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio配置
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/19
 */
@ConfigurationProperties(value = MinioProperties.MINIO_PREFIX)
@Data
public class MinioProperties {
    /**
     * 前缀
     */
    public static final String MINIO_PREFIX = "minio";

    /**
     * 地址
     */
    private String endPoint;
    /**
     * 访问密钥
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;

    /**
     * bucket:桶
     */
    private String bucket;

    /**
     * 区域
     */
    private String region = "zh-test";

}
