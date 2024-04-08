package com.yizlan.gelato.core.enums;

import com.yizlan.gelato.core.util.EnumUtils;

public enum WarningSignEnum implements TernaryEnum<String> {
    // 红色
    RED("RED", "红色", "#f5222d"),
    // 黄色
    YELLOW("YELLOW", "黄色", "#fadb14"),
    // 绿色
    GREEN("GREEN", "绿色", "#52c41a");

    private final String value;

    private final String text;

    private final String desc;

    WarningSignEnum(String value, String text, String desc) {
        this.value = value;
        this.text = text;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static WarningSignEnum getEnumByValue(String value) {
        return EnumUtils.getEnumByValue(WarningSignEnum.class, value);
    }
}
