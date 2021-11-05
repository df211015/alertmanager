package com.taoche.alertmanage.dto;

import lombok.Data;

@Data
public class RestrainItemDto {
    private Integer interval;
    private Integer maxCount;
    private Integer lock;
}
