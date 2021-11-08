package com.taoche.alertmanage.service.impl;

import com.alibaba.fastjson.JSON;
import com.taoche.alertmanage.constants.ELockStatus;
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

import java.util.Map;

/**
 * ip频次预警
 */
@Service(value = "AlertManageProcessOfIp")
@Slf4j
public class AlertManageProcessOfIp extends AbsAlertManageProcess {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess(String observeKey) {
        try {
            //获取redis监控key
            String redisObserveKey = RedisKey.formatKey(RedisKey.ALERT_IP, observeKey);
            //获取ip的约束参数
            Map<String, Integer> alertManage_param_ip = BaseDataService.AlertManage_Param_Ip;
            RestrainItemDto restrainItemDto = super.getRestrainItemDto(alertManage_param_ip);
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
                            log.info(String.format("ip:%s,已达最高访问次数:%s,被锁定:%s分钟,访问失败", observeKey, restrainItemDto.getMaxCount(), restrainItemDto.getLockTime()));
                            return GenerateResultFactory.generateSuccessResultOfMsg("已达最高访问次数!", null);
                        }
                    } else {
                        log.info(String.format("ip:%s,已被锁定,访问失败", observeKey));
                        return GenerateResultFactory.generateSuccessResultOfMsg("访问被锁定!", null);
                    }
                }
            }
        } catch (Exception ex) {
            log.info("AlertManageProcessOfIp.alertManageProcess处理异常", ex);
            return GenerateResultFactory.generateFailureResultOfMsg("访问异常", ex);
        }

        return GenerateResultFactory.generateSuccessResult(null);
    }
}
