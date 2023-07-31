package org.ting.common.domain;

import lombok.Data;
import org.ting.common.exception.CustomException;

import java.util.concurrent.Semaphore;

/**
 * 分页请求参数
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/4
 */
@Data
public class PageRequest {
    /**
     * 分页数量
     */
    private Long pageSize = 20L;
    /**
     * 分页号
     */
    private Long pageNum = 1L;

}
