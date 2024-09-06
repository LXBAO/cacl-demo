package com.example.rebbit.mq.tow;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lx
 * @data 2022/10/31 18:45
 */
@Component
@RabbitListener(queues = "man")
public class ManReceiver {

    @RabbitHandler
    public void process(Map testMessage , Channel channel, Message message) throws Exception {
        try {

            //手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("Receiver1消费者收到消息  : " + testMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //拒绝 重新入列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }

}
