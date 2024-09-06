package com.example.rebbit.mq.one;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lx
 * @data 2022/10/31 9:12
 */

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class Consumer2 {

    @RabbitHandler
    public void process(Map testMessage , Channel channel, Message message) throws Exception {
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉
            // 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            System.out.println("Consumer消费者收到消息2 拒绝应答 : "+testMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
