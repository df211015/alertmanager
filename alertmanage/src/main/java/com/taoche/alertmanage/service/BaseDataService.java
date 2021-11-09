package com.taoche.alertmanage.service;

import com.taoche.alertmanage.config.RestrainInfoConfig;
import com.taoche.alertmanage.constants.ProjConstants;
import com.taoche.alertmanage.dto.RestrainItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础数据配置
 */
@Service
public final class BaseDataService {
    @Autowired
    private RestrainInfoConfig restrainInfoConfig;

    /**
     * 获取ip的约束
     *
     * @return
     */
    public RestrainItemDto getRestrainOfIp() {
        String mapKey = ProjConstants.Config_restrain_ip;
        RestrainItemDto restrainItemDto = this.restrainInfoConfig.getRestrain().get(mapKey);
        return restrainItemDto;
    }

    /**
     * 获取mobile的约束
     *
     * @return
     */
    public RestrainItemDto getRestrainOfMobile() {
        String mapKey = ProjConstants.Config_restrain_mobile;
        RestrainItemDto restrainItemDto = this.restrainInfoConfig.getRestrain().get(mapKey);
        return restrainItemDto;
    }
}
