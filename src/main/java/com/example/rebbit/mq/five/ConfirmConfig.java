package com.example.rebbit.mq.five;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/2 19:36
 * 消息 备份
 */
@Configuration
public class ConfirmConfig {
    @Bean
    public Queue confirmQueue() {
        return new Queue("confirmQueue", true, false, false);
    }
    @Bean
    public DirectExchange confirmExchange() {
        DirectExchange  dd  = new DirectExchange("confirmExchange");
        dd.addArgument("alternate-exchange","backUpExchange");
        return dd;
    }
    @Bean
    public Binding confirmBindingA() {
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with("confirm.key");
    }

    @Bean
    public Queue backUpQueue() {
        return new Queue("backUpQueue", true, false, false);
    }


    @Bean
    public FanoutExchange  backUpExchange() {
        return new FanoutExchange("backUpExchange");
    }



    @Bean
    public Binding backUpBindingA() {
        return BindingBuilder.bind(backUpQueue()).to(backUpExchange());
    }

}
