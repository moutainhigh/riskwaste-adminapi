package com.my.battery.dic;

public enum EnableOrDisableDic {
    ENABLE(1, "启用"), DISABLE(0, "禁用"),;

    /** 枚举编号 */
    private Integer code;

    /** 枚举详情 */
    private String message;

    /**
     * @param code
     * @param message
     */
    private EnableOrDisableDic(final Integer code, final String message) {
        this.message = message;
        this.code    = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
