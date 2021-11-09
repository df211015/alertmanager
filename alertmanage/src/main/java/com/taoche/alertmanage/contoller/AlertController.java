package com.taoche.alertmanage.contoller;

import com.taoche.alertmanage.constants.EAlertProcessType;
import com.taoche.alertmanage.dto.ResultDto;
import com.taoche.alertmanage.service.impl.AlertManageProcessContext;
import com.taoche.alertmanage.service.impl.GenerateResultFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private AlertManageProcessContext alertManageProcessContext;

    /**
     * ip访问频率预警
     *
     * @param ip 访问ip
     * @return ResultDto
     */
    @GetMapping("/ip")
    public ResultDto alertIp(@RequestParam String ip) {
        if (StringUtils.isBlank(ip)) {
            return GenerateResultFactory.generateFailureResultOfMsg("访问ip不能为空!", null);
        }
        ResultDto resultDto = this.alertManageProcessContext.alertProcess(EAlertProcessType.IP, ip);

        return resultDto;
    }

    /**
     * 手机号访问频率预警
     *
     * @param mobile 手机号
     * @return ResultDto
     */
    @GetMapping("/mobile")
    public ResultDto alertMobile(@RequestParam String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return GenerateResultFactory.generateFailureResultOfMsg("手机号不能为空!", null);
        }
        ResultDto resultDto = this.alertManageProcessContext.alertProcess(EAlertProcessType.MOBILE, mobile);

        return resultDto;
    }

    @GetMapping("/mytest")
    public ResultDto testResult() {
        ResultDto resultDto = GenerateResultFactory.generateSuccessResult(null);
        return resultDto;
    }
}
