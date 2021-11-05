package com.taoche.alertmanage.dto;

import lombok.Data;

@Data
public class ObserveItemDto {
    /**
     * 监控主键串
     */
    private String observeItemKey;

    /**
     * 时间戳
     */
    private Long timestamp;
}
