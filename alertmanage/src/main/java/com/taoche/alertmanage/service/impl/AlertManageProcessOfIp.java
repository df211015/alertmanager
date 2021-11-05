package com.taoche.alertmanage.service.impl;

import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.redis.RedisUtil;
import com.taoche.alertmanage.service.AbsAlertManageProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ip频次预警
 */
@Service(value = "AlertManageProcessOfIp")
public class AlertManageProcessOfIp extends AbsAlertManageProcess {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDto alertManageProcess() {
        return null;
    }
}
