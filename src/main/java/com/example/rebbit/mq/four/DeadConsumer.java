package com.example.rebbit.mq.four;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/1 17:42
 */
@Component

public class DeadConsumer  {

    @RabbitListener(queues ="deadQueue")
    @RabbitHandler
    public void deadLetterConsumer1(Map testMessage , Channel channel, Message message) {
        String msg = message.toString();
        String[] msgArray = msg.split("'");

        System.out.println("死信队列接收到的消息为："  +testMessage.toString());

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
