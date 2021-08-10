package com.complone.starter.excel.enums;

/**
 * 日期格式
 *
 * @author complone
 * @date 2019/9/30
 */
public enum DatePattern {

    /**
     * None
     */
    NONE(""),

    /**
     * yyyyMMdd
     */
    YYYYMMDD("yyyyMMdd"),
    /**
     * yyyyMMddHHmmss
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
    /**
     * yyyy-MM-dd
     */
    YYYY_MM_DD("yyyy-MM-dd"),
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),;

    DatePattern(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    public String getPattern() {
        return pattern;
    }
}
