package com.example.rocket;

import com.alibaba.fastjson.JSONObject;
import com.example.rebbit.mq.RabbitMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author lx
 * @data 2022/10/31 14:27
 */
@RestController
@RequestMapping("/rocket-mq")
public class RocketMQCtr {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.sync-tag}")
    private String syncTag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.async-tag}")
    private String asyncag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.oneway-tag}")
    private String onewayTag;

    //普通模式
    @GetMapping(value = "/consumer")
    public void consumer() throws Exception {
        rocketMQTemplate.convertAndSend("first-topic", "你好,Java旅途" + UUID.randomUUID());

    }

    /**
     * 同步发送消息
     * @throws Exception
     */
    @GetMapping(value = "/synMessage")
    public void synMessage() throws Exception {
        String id = UUID.randomUUID().toString();
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送同步消息
        SendResult sendResult = rocketMQTemplate.syncSend(syncTag, message);
        String aa = JSONObject.toJSONString(sendResult);
        System.out.println(sendResult.getSendStatus() + aa);
    }

    /**
     * 异步发送消息
     * @throws Exception
     */
    @GetMapping(value = "/asynMessage")
    public void asynMessage() throws Exception {
        String id = UUID.randomUUID().toString();
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送异步消息
        rocketMQTemplate.asyncSend(asyncag, message, new SendCallbackListener(id));
        System.out.println("pushAsyncMessage finish : " + id);
    }

    /**
     * 单项发送
     * @throws Exception
     */
    @GetMapping(value = "/oneWay")
    public void oneWay() throws Exception {

        String id = UUID.randomUUID().toString();
        // 构建消息
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送单向消息
        rocketMQTemplate.sendOneWay(onewayTag, message);

    }

    /**
     * 延迟队列
     * @throws Exception
     */
    @GetMapping(value = "/delayMessage")
    public void delayMessage() throws Exception {
        String id = UUID.randomUUID().toString();
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置超时和延时推送
        // 超时时针对请求broker然后结果返回给product的耗时
        // 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
        // delayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        SendResult sendResult = rocketMQTemplate.syncSend(syncTag, message, 6 * 1000l, 3);
        System.out.println("pushDelayMessage finish : " + id + ", sendResult : " + JSONObject.toJSONString(sendResult));

    }

    /**
     * 批量发送
     * @throws Exception
     */
    @GetMapping(value = "/batchMessage")
    public void batchMessage() throws Exception {
        String id = UUID.randomUUID().toString();
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String myId = id + "---" + i;
            // 处理当前订单唯一标识
            String messageStr = "order id : " + myId;
            Message<String> message = MessageBuilder.withPayload(messageStr)
                    .setHeader(RocketMQHeaders.KEYS, myId)
                    .build();
            messages.add(message);
        }
        // 批量下发消息到broker,不支持消息顺序操作，并且对消息体有大小限制（不超过4M）

        rocketMQTemplate.syncSend(syncTag, messages);

    }

    /***
     * 事务
     * @throws Exception
     */
    @GetMapping(value = "/transactionMessage")
    public void transactionMessage() throws Exception {
        String id = UUID.randomUUID().toString();
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .setHeader("money", 10)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, id)
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(syncTag, message, null);
        System.out.println("pushTransactionMessage result : " + JSONObject.toJSONString(transactionSendResult));

    }
}
