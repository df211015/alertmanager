package com.taoche.alertmanage.service.impl;

import com.taoche.alertmanage.constants.EAlertProcessType;
import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.service.AbsAlertManageProcess;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlertManageProcessContext {

    private AbsAlertManageProcess absAlertManageProcessOfIp;

    private AbsAlertManageProcess getAbsAlertManageProcessOfMobile;

    private final Map<EAlertProcessType, AbsAlertManageProcess> alertProcessTypeMap = new HashMap<>();

    public AlertManageProcessContext(@Qualifier("AlertManageProcessOfIp") AbsAlertManageProcess absAlertManageProcessOfIp, @Qualifier("AlertManageProcessOfMobile") AbsAlertManageProcess getAbsAlertManageProcessOfMobile) {
        this.absAlertManageProcessOfIp = absAlertManageProcessOfIp;
        this.getAbsAlertManageProcessOfMobile = getAbsAlertManageProcessOfMobile;
        this.buildAlertProcessTypeMap();
    }

    private void buildAlertProcessTypeMap() {
        this.alertProcessTypeMap.put(EAlertProcessType.IP, this.absAlertManageProcessOfIp);
        this.alertProcessTypeMap.put(EAlertProcessType.MOBILE, this.getAbsAlertManageProcessOfMobile);
    }

    /**
     * 预警处理
     *
     * @param alertProcessType
     * @return
     */
    public ResultDto alertProcess(EAlertProcessType alertProcessType) {
        AbsAlertManageProcess absAlertManageProcess = this.alertProcessTypeMap.get(alertProcessType);
        if (null == absAlertManageProcess) {
            return GenerateResultFactory.generateFailureResultOfMsg("未找到处理策略类!", null);
        }

        ResultDto resultDto = absAlertManageProcess.alertManageProcess();
        return resultDto;
    }
}
