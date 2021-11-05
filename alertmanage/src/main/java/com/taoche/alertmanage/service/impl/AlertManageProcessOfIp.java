package com.taoche.alertmanage.service.impl;

import com.taoche.alertmanage.constants.ProjConstants;
import com.taoche.alertmanage.dto.RestrainItemDto;
import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.redis.RedisKey;
import com.taoche.alertmanage.redis.RedisUtil;
import com.taoche.alertmanage.service.AbsAlertManageProcess;
import com.taoche.alertmanage.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ip频次预警
 */
@Service(value = "AlertManageProcessOfIp")
public class AlertManageProcessOfIp extends AbsAlertManageProcess {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess(String observeKey) {
        //获取redis监控key
        String redisObserveKey = RedisKey.formatKey(RedisKey.ALERT_IP, observeKey);
        //获取ip的约束参数
        Map<String, Integer> alertManage_param_ip = BaseDataService.AlertManage_Param_Ip;
        RestrainItemDto restrainItemDto = super.getRestrainItemDto(alertManage_param_ip);

        return GenerateResultFactory.generateFailureResult(null);
    }
}
