package com.complone.starter.excel.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @date 2019/9/27
 */
@Getter
@Setter
public class ExcelProperties implements Serializable{

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 表头名称
     */
    private String alis;

    /**
     * 输出顺序，数值越大越往后
     */
    private Integer order;

    /**
     * 日期格式
     */
    private String datePattern;

    /**
     * 枚举类
     */
    private Class clazz;

    public ExcelProperties(String fieldName, String alis, Integer order, String datePattern, Class clazz) {
        this.fieldName = fieldName;
        this.alis = alis;
        this.order = order;
        this.datePattern = datePattern;
        this.clazz = clazz;
    }
}
