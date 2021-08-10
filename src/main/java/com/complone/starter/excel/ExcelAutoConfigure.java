package com.complone.starter.excel;

import com.complone.starter.excel.processor.ExcelWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author complone
 * @date 2019/9/29
 */
@Configuration
public class ExcelAutoConfigure {


    @ConditionalOnClass(name = {"cn.hutool.poi.excel.ExcelUtil"})
    @Bean
    public ExcelWrapper excelProperties(){
        return new ExcelWrapper();
    }
}
