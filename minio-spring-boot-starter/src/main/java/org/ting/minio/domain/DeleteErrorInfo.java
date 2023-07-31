package org.ting.minio.domain;

import lombok.Data;

/**
 * 删除失败的信息
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/20
 */
@Data
public class DeleteErrorInfo {

    /**
     * 错误消息
     */
    private String msg;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * bucket
     */
    private String bucket;

}
