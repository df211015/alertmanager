package com.taoche.alertmanage.redis;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Random;

public class RedisKey {
    //审核持有的车源Id池
    public final static String AUDIT_Hold_POOL = "op:audit:hold:pool";
    //会员车源审核人员key
    public final static String CARSOURCELOG_USER_PREFIX = "B:carsourelog_user";
    //易车伙伴敏感词
    public final static String DEALER_SSTWORDS_PREFIX = "B:sstivewords:";

    public final static String CARSOURCE_SNAPSHOT = "op:carsourcesnapshot:";
    //用户信息
    public final static Integer USER_EXPIRETIME = 3600 * 24 * 365 + new Random().nextInt(100);
    public final static Long SMS_VERIFY_EXPIRETIME = 60 * 10L;

    public final static Long LOGIN_USER_EXPIRETIME = 15 * 24 * 3600L + new Random().nextInt(100);

    public final static Long CARSOURCELOG_USER_EXPIRETIME= 12L;

    //快照唯一性过期时间,有效key
    public final static Long CARSOURCE_SNAPSHOT_EXPIRETIME = 60 * 15L;

    //快照唯一性过期时间,无效key
    public final static Long CARSOURCE_SNAPSHOT_EXPIRETIME_UNAVAIL = 60 * 3L;

    public static String formatKey(String prefix, String targetKey) {
        if (StringUtils.isNotBlank(prefix) && StringUtils.isNotBlank(targetKey)) {
            return MessageFormat.format("{0}{1}", prefix, targetKey);
        }

        return null;
    }
}