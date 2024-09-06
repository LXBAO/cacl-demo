package com.example.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author lx
 * @data 2022/11/15 20:42
 */
/**
 * selectorExpression同步，异步，单项发送
 * messageModel 定义消费的模式  广播或者集群
 * consumeMode.ORDERLY消息循序,consumeMode.CONCURRENTLY
 */

@Component
@RocketMQMessageListener(consumerGroup = "anran-consumer-group", topic = "anran-topic",
        selectorExpression = "syn||asyn||way", messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY)
public class RocketConsumer implements RocketMQListener , RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(Object o) {
        System.out.println("Received message  : "+ o);
        //业务代码抛出异常后到达最大重试次数直接进入死信队列
        //throw new RuntimeException("Consumer Message exceotion");
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(6);
        //defaultMQPushConsumer.setPullBatchSize();
    }
}