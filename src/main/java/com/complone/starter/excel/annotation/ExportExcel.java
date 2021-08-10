package com.complone.starter.excel.annotation;

import com.complone.starter.excel.enums.DatePattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出注解* @date 2019/9/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportExcel {

    /**
     * 单元格位置，从0开始
     *
     * @return
     */
    int order();

    /**
     * 隐射表头名称
     *
     * @return
     */
    String alisName();

    /**
     * 日期格式
     *
     * @return
     */
    DatePattern datePattern() default DatePattern.NONE;

    /**
     * 枚举类型
     * @return
     */
    Class enumClass() default Void.class;
}
