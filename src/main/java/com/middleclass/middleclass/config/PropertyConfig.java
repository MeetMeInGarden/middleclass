package com.middleclass.middleclass.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

// property 설정 class
@Configuration
public class PropertyConfig {
    @Bean(name = "key")
    public PropertiesFactoryBean keyPropertiesBean() {
        return this.factory("properties/key.properties");
    }

    // 다른 proterties 파일도 아래에 추가
    @Bean(name = "db")
    public PropertiesFactoryBean dbPropertiesBean() {
        return this.factory("properties/db.properties");
    }

    private PropertiesFactoryBean factory(String property) {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource(property); // src/main/resources 가 기본으로 붙는다.
        propertiesFactoryBean.setLocation(classPathResource);
        return propertiesFactoryBean;
    }
}