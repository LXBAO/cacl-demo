package com.example.rebbit.mq.five;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/2 20:12
 */
@Component
public class WarningConsumer {
    @RabbitListener(queues ="backUpQueue")
    @RabbitHandler
    public void deadLetterConsumer1(Map testMessage , Channel channel, Message message) {

        System.out.println("报警的消息为："  +testMessage.toString());

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
