package com.example.rebbit.mq.tow;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * @author lx
 * @data 2022/10/31 18:43  发布订阅模
 */
@Configuration
public class RabbitConfig {
    public final static String man = "man";
    public final static String woman = "woman";

    @Bean
    public Queue firstQueue() {
        return new Queue(RabbitConfig.man,true,true,false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(RabbitConfig.woman,true,true,false);
    }

    @Bean

    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingFanoutExchangeA() {
        return BindingBuilder.bind(firstQueue())   //绑定队列
                .to(fanoutExchange());       //队列绑定到哪个交换器
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingFanoutExchangeB() {
        return BindingBuilder.bind(secondQueue()).to(fanoutExchange());
    }

}
