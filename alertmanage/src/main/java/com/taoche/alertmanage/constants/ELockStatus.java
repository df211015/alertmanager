package com.taoche.alertmanage.constants;

import java.util.*;

public enum ELockStatus {
    UNLOCK(0, "unlock"),
    LOCKED(1, "locked"),
    ;

    private Integer code;
    private String description;

    private ELockStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ELockStatus findByCode(Integer code) {
        if (null == code) {
            return null;
        }

        for (ELockStatus lockStatus : ELockStatus.values()) {
            if (code.equals(lockStatus.code)) {
                return lockStatus;
            }
        }
        return null;
    }

    public static String findDescriptionByCode(Integer code) {
        if (null == code) {
            return null;
        }

        for (ELockStatus lockStatus : ELockStatus.values()) {
            if (code.equals(lockStatus.code)) {
                return lockStatus.description;
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
        Arrays.asList(ELockStatus.values())
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
