package com.yizlan.gelato.core.enums;

import com.yizlan.gelato.core.util.EnumUtils;

public enum WarningSignEnum implements TernaryEnum<String> {
    // 红色
    RED("RED", "#f5222d", "红色"),
    // 黄色
    YELLOW("YELLOW", "#fadb14", "黄色"),
    // 绿色
    GREEN("GREEN", "#52c41a", "绿色");

    private final String value;

    private final String label;

    private final String desc;

    WarningSignEnum(String value, String label, String desc) {
        this.value = value;
        this.label = label;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static WarningSignEnum getEnumByValue(String value) {
        return EnumUtils.getEnumByValue(WarningSignEnum.class, value);
    }
}
