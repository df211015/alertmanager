package com.taoche.alertmanage.service;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 基础数据配置
 */
public final class BaseDataService {

    /**
     * ip预警配置参数
     * interval, 间隔时间, 单位:分钟
     * maxCount, 阀值, 单位:个
     * lock, 锁定, 单位:分钟
     */
    public static Map<String, Integer> AlertManage_Param_Ip = ImmutableMap.<String, Integer>builder()
            .put("interval", 10)
            .put("maxCount", 200)
            .put("lock", 5)
            .build();

    /**
     * 短信预警配置参数
     * interval, 间隔时间, 单位:分钟
     * maxCount, 阀值, 单位:个
     * lock, 锁定, 单位:分钟
     */
    public static Map<String, Integer> AlertManage_Param_Mobile = ImmutableMap.<String, Integer>builder()
            .put("interval", 60)
            .put("maxCount", 5)
            .put("lock", 5)
            .build();
}
