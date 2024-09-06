package com.example.rebbit.mq.three;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/1 9:11
 */
@Component
@RabbitListener(queues = "rabbit_topic_queue_2")
public class TopRabbitConsumer2 {
    @RabbitHandler
    public void process(Map testMessage , Channel channel, Message message) throws Exception {
        try {
            //手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("rabbit_topic_queue_2  : " + testMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //拒绝 重新入列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }
}