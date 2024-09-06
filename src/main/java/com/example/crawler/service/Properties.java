package com.example.crawler.service;

import com.example.crawler.vo.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author lx
 * @data 2023/3/21 10:18
 */
@Component
@Data
@PropertySource(value = {"classpath:test.yml"},factory =  YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "test")
public class Properties {
   public User user;
}
