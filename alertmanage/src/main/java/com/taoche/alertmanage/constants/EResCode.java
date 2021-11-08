package com.taoche.alertmanage.constants;

/**
 * 返回code定义
 */
public enum EResCode {
    SUCCESS(1, "成功"),
    Fail(400, "失败"),
    Exception_generic(401, "异常"),
    Locked(420, "访问锁定"),
    Locked_maxCount(421, "ip访问超过阀值被锁定"),
    ;
    private Integer code;
    private String description;

    private EResCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
