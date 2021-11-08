package com.taoche.alertmanage.redis;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public final class RedisKey {

    /**
     * ip预警key前缀
     */
    public final static String ALERT_IP = "alertmanage:ip";

    /**
     * 手机号预警key前缀
     */
    public final static String ALERT_MOBILE = "alertmanage:mobile";

    /**
     * 格式化redisKey
     * @param prefix
     * @param targetKey
     * @return
     */
    public static String formatKey(String prefix, String targetKey) {
        if (StringUtils.isNotBlank(prefix) && StringUtils.isNotBlank(targetKey)) {
            return MessageFormat.format("{0}:{1}", prefix, targetKey);
        }

        return null;
    }
}