package com.taoche.alertmanage.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 电话频次预警
 */
@Slf4j
@Service(value = "AlertManageProcessOfMobile")
public class AlertManageProcessOfMobile extends AbsAlertManageProcess {
    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess(String observeKey) {
        try {
            //获取redis监控key
            String redisObserveKey = RedisKey.formatKey(RedisKey.ALERT_MOBILE, observeKey);
            //获取约束参数
            RestrainItemDto restrainItemDto = this.baseDataService.getRestrainOfMobile();
            Boolean hasKey = this.redisUtil.hasHaKey(redisObserveKey);
            if (hasKey) {
                Object obj = this.redisUtil.getHa(redisObserveKey);
                ObserveItemDto observeItemDto = JSON.parseObject(JSON.toJSONString(obj), ObserveItemDto.class);
                if (null != observeItemDto) {
                    Integer isLock = observeItemDto.getIsLock();
                    if (ELockStatus.UNLOCK.getCode().equals(isLock)) {
                        Integer visitCount = (null != observeItemDto.getVisitCount()) ? observeItemDto.getVisitCount() : 0;
                        if (visitCount < restrainItemDto.getMaxCount()) {
                            //指定业务键在未锁定状态进入计数加数处理
                            observeItemDto.setVisitCount(visitCount + 1);
                            observeItemDto.setIsLock(ELockStatus.UNLOCK.getCode());
                            this.redisUtil.setHa(redisObserveKey, JSON.toJSONString(observeItemDto), observeItemDto.getTimestamp());
                        } else {
                            Long timeInMillis = super.getTimeInMillis();
                            observeItemDto = super.buildObserveItemDto(redisObserveKey, visitCount, timeInMillis, restrainItemDto.getLockTime(), ELockStatus.LOCKED.getCode());
                            this.redisUtil.setHa(redisObserveKey, JSON.toJSONString(observeItemDto), observeItemDto.getTimestamp());
                            log.info(String.format("mobile:%s,已达最高访问次数:%s,被锁定:%s分钟,访问失败", observeKey, restrainItemDto.getMaxCount(), restrainItemDto.getLockTime()));
                            return GenerateResultFactory.generateFailureResult(EResCode.Locked_maxCount, null);
                        }
                    } else {
                        log.info(String.format("mobile:%s,已被锁定,访问失败", observeKey));
                        return GenerateResultFactory.generateFailureResult(EResCode.Locked, null);
                    }
                }
            } else {
                Long timeInMillis = super.getTimeInMillis();
                ObserveItemDto observeItemDto = super.buildObserveItemDto(redisObserveKey, 1, timeInMillis, restrainItemDto.getLockTime(), ELockStatus.UNLOCK.getCode());
                this.redisUtil.setHa(redisObserveKey, JSON.toJSONString(observeItemDto), observeItemDto.getTimestamp());
            }
        } catch (Exception ex) {
            log.info("AlertManageProcessOfIp.alertManageProcess处理异常", ex);
            return GenerateResultFactory.generateFailureResultOfMsg("访问异常", ex);
        }

        return GenerateResultFactory.generateSuccessResult(null);
    }
}
