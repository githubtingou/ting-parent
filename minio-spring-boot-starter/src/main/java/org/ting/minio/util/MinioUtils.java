package org.ting.minio.util;

import com.google.common.net.HttpHeaders;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.ting.minio.config.CustomMinioAsyncClient;
import org.ting.minio.config.MinioProperties;
import org.ting.minio.domain.BucketInfo;
import org.ting.minio.domain.DeleteErrorInfo;
import org.ting.minio.exception.CustomMinioException;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * minio使用
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtils {

    private final CustomMinioAsyncClient minioClient;

    private final MinioProperties minioProperties;

    /**
     * 获取MinioClient
     *
     * @return {@link MinioClient}
     */
    public CustomMinioAsyncClient getMinioClient() {
        return minioClient;
    }

    /**
     * 文件上传
     *
     * @param multipartFile {@link MultipartFile} 文件流
     * @param bucket        bucket
     * @param fileName      文件名
     * @return 文件地址
     */
    public String upload(MultipartFile multipartFile, String bucket, String fileName) {
        if (log.isDebugEnabled()) {
            log.debug("minio-文件上传：bucket=[{}],fileName=[{}]", bucket, fileName);
        }
        try {
            if (!this.bucketExists(bucket)) {
                this.createBucket(bucket);
            }
            InputStream stream = multipartFile.getInputStream();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(StringUtils.hasLength(bucket) ? bucket : this.minioProperties.getBucket())
                    .object(fileName)
                    .contentType(multipartFile.getContentType())
                    .stream(stream, multipartFile.getSize(), -1)
                    .build();
            this.getMinioClient().putObject(putObjectArgs);
            return getUrl(bucket, fileName);

        } catch (Exception e) {
            log.error("minio-文件上传失败：bucket=[{}],fileName=[{}]", bucket, fileName, e);
            throw new CustomMinioException(e);
        }

    }

    /**
     * 文件上传
     *
     * @param file     {@link File}
     * @param bucket   bucket
     * @param fileName 文件名
     * @return
     */
    public String upload(File file, String bucket, String fileName) {
        if (log.isDebugEnabled()) {
            log.debug("minio-文件上传：bucket=[{}],fileName=[{}]", bucket, fileName);
        }
        try {
            if (!this.bucketExists(bucket)) {
                this.createBucket(bucket);
            }

            InputStream stream = Files.newInputStream(file.toPath());
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(StringUtils.hasLength(bucket) ? bucket : this.minioProperties.getBucket())
                    .object(fileName)
                    .stream(stream, file.length(), -1)
                    .build();
            this.getMinioClient().putObject(putObjectArgs);
            return getUrl(bucket, fileName);

        } catch (Exception e) {
            log.error("minio-文件上传失败：bucket=[{}],fileName=[{}]", bucket, fileName, e);
            throw new CustomMinioException(e);
        }
    }

    /**
     * 获取文件地址
     *
     * @param bucket   bucket
     * @param fileName 文件名
     * @return 文件地址
     */
    public String getUrl(String bucket, String fileName) {
        if (log.isDebugEnabled()) {
            log.debug("minio-获取文件地址：bucket=[{}],fileName=[{}]", bucket, fileName);

        }
        GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucket)
                .object(fileName).build();
        try {
            return this.getMinioClient().getPresignedObjectUrl(urlArgs);
        } catch (Exception e) {
            log.error("minio-获取文件地址：bucket=[{}] ,fileName=[{}]", bucket, fileName, e);
            throw new CustomMinioException("获取minio地址失败", e);
        }

    }

    /**
     * 判断bucket是否存在
     *
     * @param bucket bucket
     * @return false：不存在 true：存在
     */
    public boolean bucketExists(String bucket) {
        if (log.isDebugEnabled()) {
            log.debug("minio-判断是否存在：bucket=[{}]", bucket);
        }
        try {
            this.getMinioClient().bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("minio-判断bucket是否存在失败：bucket=[{}]", bucket, e);
            throw new CustomMinioException(e);
        }

        return true;
    }

    /**
     * 创建bucket
     *
     * @param bucket bucket
     * @return false：失败 true：成功
     */
    public boolean createBucket(String bucket) {
        if (log.isDebugEnabled()) {
            log.debug("minio-创建bucket=[{}]", bucket);
        }
        try {
            this.getMinioClient().makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("minio-创建bucket=[{}]失败", bucket, e);
            throw new CustomMinioException(e);
        }
        return true;
    }

    /**
     * 文件下载
     *
     * @param response {@link HttpServletResponse}
     * @param bucket   bucket
     * @param fileName 文件名
     */
    public void download(HttpServletResponse response, String bucket, String fileName) {
        if (log.isDebugEnabled()) {
            log.debug("minio-文件下载,bucket=[{}] ,fileName=[{}]", bucket, fileName);
        }
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucket).object(fileName).build();
        try (GetObjectResponse object = this.getMinioClient().getObject(objectArgs).get()) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = object.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();

                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = response.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            log.error("minio-文件下载失败,bucket=[{}] ,fileName=[{}]", bucket, fileName, e);
            throw new CustomMinioException("文件下载失败", e);
        }
    }

    /**
     * 删除文件
     *
     * @param bucket   bucket
     * @param fileName bucket
     * @return
     */
    public boolean delFile(String bucket, String fileName) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("minio-批量删除文件入参,bucket=[{}] ,fileNames=[{}]", bucket, fileName);
            }
            RemoveObjectArgs objectArgs = RemoveObjectArgs.builder().bucket(bucket).object(fileName).build();
            this.getMinioClient().removeObject(objectArgs);
            return true;
        } catch (Exception e) {
            log.error("minio-删除文件失败,bucket=[{}] ,fileName=[{}]", bucket, fileName);
            throw new CustomMinioException("minio-删除文件失败", e);
        }
    }

    /**
     * 批量删除文件
     *
     * @param bucket      bucket
     * @param fileNameSet bucket
     * @return 返回删除失败的文件，为空表示已全部删除
     */
    public List<DeleteErrorInfo> delFiles(String bucket, Set<String> fileNameSet) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("minio-批量删除文件入场,bucket=[{}] ,fileNames=[{}]", bucket, fileNameSet);
            }
            List<DeleteErrorInfo> errorList = new ArrayList<>();
            List<DeleteObject> deleteObjects = fileNameSet.stream().map(DeleteObject::new).collect(Collectors.toList());
            RemoveObjectsArgs objectArgs = RemoveObjectsArgs.builder().bucket(bucket).objects(deleteObjects).build();
            Iterable<Result<DeleteError>> results = this.getMinioClient().removeObjects(objectArgs);
            results.forEach(item -> {
                try {
                    DeleteError deleteError = item.get();
                    DeleteErrorInfo info = new DeleteErrorInfo();
                    info.setBucket(deleteError.bucketName());
                    info.setFileName(deleteError.objectName());
                    info.setMsg(deleteError.message());
                    errorList.add(info);
                } catch (Exception e) {
                    log.error("minio-批量删除文件获取失败数据异常,bucket=[{}] ,fileNames=[{}]", bucket, fileNameSet);
                    throw new CustomMinioException(e);
                }
            });
            return errorList;
        } catch (Exception e) {
            log.error("minio-批量删除文件失败,bucket=[{}] ,fileNames=[{}]", bucket, fileNameSet);
            throw new CustomMinioException("minio-删除文件失败", e);
        }
    }

    /**
     * 获取所有bucket信息
     *
     * @return BucketInfo集合
     */
    public List<BucketInfo> getBuckInfoList() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("获取所有bucket信息");
            }
            List<Bucket> buckets = this.getMinioClient().listBuckets().get();
            return buckets.stream().map(bucket -> {
                BucketInfo bucketInfo = new BucketInfo();
                bucketInfo.setBucket(bucket.name());
                bucketInfo.setCreateTime(bucket.creationDate().toLocalDateTime());
                return bucketInfo;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomMinioException("minio-获取所有bucket信息失败", e);
        }
    }

}
