package com.taoche.alertmanage.service;

import com.alibaba.fastjson.JSON;
import com.taoche.alertmanage.constants.ProjConstants;
import com.taoche.alertmanage.dto.ObserveItemDto;
import com.taoche.alertmanage.dto.RestrainItemDto;
import com.taoche.alertmanage.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Map;

/**
 * 频次预警
 */
@Slf4j
public abstract class AbsAlertManageProcess {

    /**
     * 获取当前时间点
     *
     * @return Long
     */
    protected Long getTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return timeInMillis;
    }

    /**
     * 构建监控实体
     *
     * @param observeItemKey 业务主键key
     * @param visitCount     访问次数
     * @param timeInMillis   当时时间点
     * @param minute         过期时间,单位:分钟
     * @param isLock         是否锁定
     * @return ObserveItemDto
     */
    protected ObserveItemDto buildObserveItemDto(String observeItemKey, Integer visitCount, Long timeInMillis, Integer minute, Integer isLock) {
        if (null != observeItemKey) {
            ObserveItemDto observeItemDto = new ObserveItemDto();
            observeItemDto.setObserveItemKey(observeItemKey);
            observeItemDto.setVisitCount(visitCount);
            Integer addMillis = minute * 60 * 1000;
            Long expireMilis = timeInMillis + addMillis;
            observeItemDto.setTimestamp(expireMilis);
            log.info(String.format("构建监控实体,buildObserveItemDto:%s", JSON.toJSONString(observeItemDto)));
            return observeItemDto;
        }

        log.info("构建监控实体,buildObserveItemDto,业务key为空");
        return null;
    }

    /**
     * 预警处理
     *
     * @param observeKey 监控key
     * @return
     */
    public abstract ResultDto alertManageProcess(String observeKey);
}
