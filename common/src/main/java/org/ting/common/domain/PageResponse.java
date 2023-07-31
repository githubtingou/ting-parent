package org.ting.common.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页返参
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/5
 */
@Data
public class PageResponse<T> {
    /**
     * 总数
     */
    private Long total = 0L;
    /**
     * 数据
     */
    private List<T> result = new ArrayList<>();
}
