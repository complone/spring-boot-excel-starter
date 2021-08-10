package com.complone.starter.excel.convert;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * 日期格式转换
 *
 * @author complone
 * @date 2019/9/29
 */
public class DateConvert implements Convert {

    private final String pattern;

    public DateConvert(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Object convert(Object value) {
        if (null == value || !(value instanceof Date) || StrUtil.isEmpty(pattern)) {
            return null;
        }
        return DateUtil.format((Date) value, pattern);
    }
}
