package com.my.battery.util;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumBase {

    /**
     * @return jackson序列化的值
     */
    @JsonValue
    int getCode();

    String getName();
}
