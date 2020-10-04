package com.my.battery.dic;

public enum PermissionTypeDic {
    PATH(1, "目录"), MENU(2, "菜单"), P001(3, "按钮"),;

    /** 枚举编号 */
    private Integer code;

    /** 枚举详情 */
    private String message;

    /**
     * @param code
     * @param message
     */
    private PermissionTypeDic(final Integer code, final String message) {
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
