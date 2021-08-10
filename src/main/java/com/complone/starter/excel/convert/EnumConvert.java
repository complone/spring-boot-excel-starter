package com.complone.starter.excel.convert;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.Map;
import java.util.Optional;

/**
 * 枚举转换
 *
 * @author complone
 * @date 2019/9/29
 */
public class EnumConvert implements Convert {

    private final Class clazz;
    /**
     * 取枚举的desc字段值
     */
    private final String DESC = "desc";
    /**
     * 取枚举值code字段
     */
    private final String CODE = "code";

    public EnumConvert(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object convert(Object value) {
        if (null == value || !EnumUtil.isEnum(clazz)) {
            return null;
        }
        Map<String, Enum> enumMap = EnumUtil.getEnumMap(clazz);
        if (enumMap.isEmpty()) {
            return null;
        }
        Optional<Enum> target = enumMap.values().stream().filter(enumObject -> value.equals(ReflectUtil.getFieldValue(enumObject, CODE))).findFirst();
        return null == target ? null : ReflectUtil.getFieldValue(target.get(), DESC);
    }

}
