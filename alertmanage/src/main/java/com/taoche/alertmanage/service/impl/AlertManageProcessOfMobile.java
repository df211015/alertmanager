package com.taoche.alertmanage.service.impl;

import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.redis.RedisUtil;
import com.taoche.alertmanage.service.AbsAlertManageProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 电话频次预警
 */
@Service(value = "AlertManageProcessOfMobile")
public class AlertManageProcessOfMobile extends AbsAlertManageProcess {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess() {

        return GenerateResultFactory.generateSuccessResult(null);
    }
}
