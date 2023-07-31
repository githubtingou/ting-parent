package org.ting.minio.config;

import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.ting.minio.exception.CustomMinioException;

/**
 * minio配置
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/19
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingBean(value = MinioClient.class)
@EnableConfigurationProperties(value = {MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties minioProperties;

    @Bean
    public CustomMinioAsyncClient minioClient() {
        CustomMinioAsyncClient client;
        if (log.isDebugEnabled()) {
            log.debug("minio初始化,endPoint=[{}],accessKey=[{}],secretKey=[{}],bucket=[{}]",
                    this.minioProperties.getEndPoint(),
                    this.minioProperties.getAccessKey(),
                    this.minioProperties.getSecretKey(),
                    this.minioProperties.getRegion());
        }
        try {
            log.info("======minio初始化完成======");
            return new CustomMinioAsyncClient(MinioAsyncClient.builder()
                    .endpoint(this.minioProperties.getEndPoint())
                    .credentials(this.minioProperties.getAccessKey(), this.minioProperties.getSecretKey())
                    .region(this.minioProperties.getRegion())
                    .build());
        } catch (Exception e) {
            log.error("minio初始化错误,请检查相应的配置", e);
            throw new CustomMinioException(e);
        }

    }

}
