package com.complone.starter.excel.processor;

import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.complone.starter.excel.annotation.ExportExcel;
import com.complone.starter.excel.convert.DateConvert;
import com.complone.starter.excel.convert.DefaultConvert;
import com.complone.starter.excel.convert.EnumConvert;
import com.complone.starter.excel.config.ExcelProperties;
import com.complone.starter.excel.convert.Convert;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author complone
 * @date 2019/9/27
 */
public class ExcelWrapper {


    /**
     * 创建Exportor
     *
     * @param clazz   要转换的类
     * @param rowData 数据列表
     * @return
     */
    public byte[] toByteArray(Class clazz, List rowData) {
        return new Exportor(clazz, rowData).export();
    }

    /**
     * 创建
     *
     * @param clazz   要转换的类
     * @param rowData 数据列表
     * @param title   标题
     * @return
     */
    public byte[] toByteArray(Class clazz, List rowData, String title) {
        return new Exportor(clazz, rowData).export();
    }


    @Data
    @NoArgsConstructor
    class Exportor {

        private Class clazz;

        private List rowData;

        private String title;

        private boolean isMerge;

        private List<ExcelProperties> properties;

        public Exportor(Class clazz, List rowData) {
            this.clazz = clazz;
            this.rowData = rowData;
            this.parse();
        }

        public Exportor(Class clazz, List rowData, String title) {
            this(clazz, rowData);
            this.title = title;
        }

        /**
         * 第一行合并成标题，如果传入Empty，则默认不合并
         *
         * @param title
         * @return
         */
        public Exportor merge(String title) {
            this.title = title;
            this.isMerge = (null != title && !"".equals(title.trim()));
            return this;
        }

        /**
         * 输出
         *
         * @return
         */
        public byte[] export() {
            if (null == properties || properties.size() == 0) {
                throw new RuntimeException("properties is null,请检查配置是否正确");
            }
            ExcelWriter writer = ExcelUtil.getWriter(true);
            properties.sort(Comparator.comparing(ExcelProperties::getOrder));
            properties.stream().forEachOrdered((ExcelProperties p) -> writer.addHeaderAlias(p.getFieldName(), p.getAlis()));
            writer.write(toMapList(), true);
            if (isMerge) {
                writer.merge(properties.size() - 1, title);
            }
            writer.autoSizeColumnAll();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            writer.flush(out, true);
            return out.toByteArray();
        }

        /**
         * 解析
         *
         * @return
         */
        private void parse() {
            if (null == clazz) {
                throw new RuntimeException("class is null,请将要转换的对象传入");
            }
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length == 0) {
                return;
            }
            properties = new ArrayList<>();
            Arrays.stream(fields).forEach((Field field) -> {
                if (!field.isAnnotationPresent(ExportExcel.class)) {
                    return;
                }
                ExportExcel excel = field.getAnnotation(ExportExcel.class);
                ExcelProperties properties = new ExcelProperties(
                        field.getName(),
                        excel.alisName(),
                        excel.order(),
                        excel.datePattern().getPattern(),
                        excel.enumClass()
                );
                this.properties.add(properties);
            });
        }

        /**
         * 转换成Map格式，只有含<code>@ExportExcel</code>注解的才会做转换
         *
         * @return
         */
        private List<Map<String, Object>> toMapList() {
            if (CollectionUtil.isEmpty(rowData)) {
                throw new RuntimeException("data is empty,无导出数据");
            }
            List<Map<String, Object>> mapList = new ArrayList<>(rowData.size());
            rowData.forEach(row -> {
                DynaBean bean = DynaBean.create(row);
                Map<String, Object> map = new HashMap<>(properties.size());
                properties.forEach(p -> {
                    String fieldName = p.getFieldName();
                    map.put(fieldName, convert(bean.get(fieldName), p));
                });
                mapList.add(map);
            });
            return mapList;
        }

        /**
         * 时间格式转换
         * 枚举转换
         * 金额格式转换等
         *
         * @param beforeValue 转换前的值
         * @param annotation  字段注解属性
         * @return 转换后的值
         */
        private Object convert(Object beforeValue, ExcelProperties annotation) {
            Convert convert;
            if (beforeValue instanceof Date) {
                convert = new DateConvert(annotation.getDatePattern());
            } else if ((annotation.getClazz() != Void.class)) {
                convert = new EnumConvert(annotation.getClazz());
            } else {
                convert = new DefaultConvert();
            }
            return null == convert ? null : convert.convert(beforeValue);
        }

    }

}
