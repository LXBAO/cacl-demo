package com.example.crawler;


import com.example.testcase.RoleDao;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "thread.pool")
@Component
public class ConfigProperties {

    RoleDao roleDao;

    private void tt(){
        //roleDao.save();
    }
}
