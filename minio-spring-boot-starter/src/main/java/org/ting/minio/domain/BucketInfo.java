package org.ting.minio.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * bucket信息
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/20
 */
@Data
public class BucketInfo {
    /**
     * bucket
     */
    private String bucket;
    /**
     * bucket创建时间
     */
    private LocalDateTime createTime;

}
