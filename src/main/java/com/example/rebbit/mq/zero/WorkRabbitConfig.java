package com.example.rebbit.mq.zero;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lx
 * @data 2022/11/1 10:00 工作模式
 */
@Configuration
public class WorkRabbitConfig {
    @Bean
    public Queue easyQueue() {
        return new Queue("rabbit_work_queue");
    }
}
