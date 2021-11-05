package com.taoche.alertmanage.dto;

import lombok.Data;

@Data
public class ObserveItemDto {
    /**
     * 监控主键串
     */
    private String observeItemKey;

    /**
     * 访问频次
     */
    private Integer visitCount;

    /**
     * 是否锁定,0:未锁定，1:已锁定
     */
    private Integer isLock;

    /**
     * 时间戳
     */
    private Long timestamp;
}
