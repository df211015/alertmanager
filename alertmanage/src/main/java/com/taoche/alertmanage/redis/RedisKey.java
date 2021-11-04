package com.taoche.alertmanage.redis;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Random;

public final class RedisKey {

    public final static String ALERT_IP = "alert:ip:";

    public final static Long ALERT_IP_EXPIRETIME = 60 * 10L;

    public static String formatKey(String prefix, String targetKey) {
        if (StringUtils.isNotBlank(prefix) && StringUtils.isNotBlank(targetKey)) {
            return MessageFormat.format("{0}{1}", prefix, targetKey);
        }

        return null;
    }
}