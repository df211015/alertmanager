package com.taoche.alertmanage.service.impl;

import com.google.gson.Gson;
import com.taoche.alertmanage.constants.ELockStatus;
import com.taoche.alertmanage.constants.EResCode;
import com.taoche.alertmanage.dto.ObserveItemDto;
import com.taoche.alertmanage.dto.RestrainItemDto;
import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.redis.RedisKey;
import com.taoche.alertmanage.redis.RedisUtil;
import com.taoche.alertmanage.service.AbsAlertManageProcess;
import com.taoche.alertmanage.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ip频次预警
 */
@Slf4j
@Service(value = "AlertManageProcessOfIp")
public class AlertManageProcessOfIp extends AbsAlertManageProcess {
    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess(String observeKey) {
        try {
            //处理ip
            String myObserveKey = observeKey.replace(".", StringUtils.EMPTY);
            //获取redis监控key
            String redisObserveKey = RedisKey.formatKey(RedisKey.ALERT_IP, myObserveKey);
            //获取约束参数
            RestrainItemDto restrainItemDto = this.baseDataService.getRestrainOfIp();
            Boolean hasKey = this.redisUtil.hasHaKey(redisObserveKey);
            if (hasKey) {
                Object obj = this.redisUtil.getHa(redisObserveKey);
                String strJson = obj.toString();
                ObserveItemDto observeItemDto = super.gson.fromJson(strJson, ObserveItemDto.class);
                if (null != observeItemDto) {
                    Integer isLock = observeItemDto.getIsLock();
                    if (ELockStatus.UNLOCK.getCode().equals(isLock)) {
                        Integer visitCount = (null != observeItemDto.getVisitCount()) ? observeItemDto.getVisitCount() : 0;
                        if (visitCount < restrainItemDto.getMaxCount()) {
                            //指定业务键在未锁定状态进入计数加数处理
                            observeItemDto.setVisitCount(visitCount + 1);
                            observeItemDto.setIsLock(ELockStatus.UNLOCK.getCode());
                            this.redisUtil.setHa(redisObserveKey, this.gson.toJson(observeItemDto), observeItemDto.getTimestamp());
                        } else {
                            observeItemDto = super.buildObserveItemDto(redisObserveKey, visitCount, restrainItemDto.getInterval(), ELockStatus.LOCKED.getCode());
                            this.redisUtil.setHa(redisObserveKey, super.gson.toJson(observeItemDto), observeItemDto.getTimestamp());
                            log.info(String.format("ip:%s,已达最高访问次数:%s,被锁定:%s分钟,访问失败", observeKey, restrainItemDto.getMaxCount(), restrainItemDto.getLockTime()));
                            return GenerateResultFactory.generateFailureResult(EResCode.Locked_maxCount, null);
                        }
                    } else {
                        log.info(String.format("ip:%s,已被锁定,访问失败", observeKey));
                        return GenerateResultFactory.generateFailureResult(EResCode.Locked, null);
                    }
                }
            } else {
                ObserveItemDto observeItemDto = super.buildObserveItemDto(redisObserveKey, 1, restrainItemDto.getInterval(), ELockStatus.UNLOCK.getCode());
                this.redisUtil.setHa(redisObserveKey, super.gson.toJson(observeItemDto), observeItemDto.getTimestamp());
            }
        } catch (Exception ex) {
            log.info("AlertManageProcessOfIp.alertManageProcess处理异常", ex);
            return GenerateResultFactory.generateFailureResultOfMsg("访问异常", ex);
        }

        return GenerateResultFactory.generateSuccessResult(null);
    }
}
