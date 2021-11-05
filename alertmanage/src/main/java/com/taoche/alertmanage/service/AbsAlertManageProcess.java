package com.taoche.alertmanage.service;

import com.taoche.alertmanage.dto.ResultDto;

/**
 * 频次预警
 */
public abstract class AbsAlertManageProcess {

    /**
     * 预警处理
     * @return
     */
    public abstract ResultDto alertManageProcess();
}
