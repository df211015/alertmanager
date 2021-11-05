package com.taoche.alertmanage.constants;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EAlertProcessType {
    IP(10, "ip"),
    MOBILE(20, "mobile"),
    ;

    private Integer code;
    private String description;

    private EAlertProcessType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EAlertProcessType findByCode(Integer code) {
        if (null == code) {
            return null;
        }

        for (EAlertProcessType alertProcessType : EAlertProcessType.values()) {
            if (code.equals(alertProcessType.code)) {
                return alertProcessType;
            }
        }
        return null;
    }

    public static String findDescriptionByCode(Integer code) {
        if (null == code) {
            return null;
        }

        for (EAlertProcessType alertProcessType : EAlertProcessType.values()) {
            if (code.equals(alertProcessType.code)) {
                return alertProcessType.description;
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;

    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;

    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static List<Map<String, Object>> getEnumEntitys() {
        List<Map<String, Object>> result = new ArrayList<>();
        Arrays.asList(EAlertProcessType.values())
                .stream()
                .forEach(en -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("code", en.getCode());
                    item.put("description", en.getDescription());
                    result.add(item);
                });
        return result;
    }
}
