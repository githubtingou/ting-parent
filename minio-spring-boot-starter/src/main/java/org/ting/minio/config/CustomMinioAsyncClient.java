package org.ting.minio.config;

import io.minio.CreateMultipartUploadResponse;
import io.minio.MinioAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.ting.minio.exception.CustomMinioException;

/**
 * @author ting
 * @version 1.0
 * @date 2023/7/26
 */
@Slf4j
public class CustomMinioAsyncClient extends MinioAsyncClient {

    protected CustomMinioAsyncClient(MinioAsyncClient client) {
        super(client);
    }

    public CreateMultipartUploadResponse createMultipartUploadAsync(String bucketName,
                                                                    String region,
                                                                    String objectName) {
        try {
            return super.createMultipartUploadAsync(bucketName, region, objectName, null, null).get();
        } catch (Exception e) {
            log.error("分页上传初始化失败,", e);
            throw new CustomMinioException(e);

        }
    }
}
