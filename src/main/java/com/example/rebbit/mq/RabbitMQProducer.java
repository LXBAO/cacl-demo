package com.example.rebbit.mq;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lx
 * @data 2022/10/31 9:11
 */

@Component
public class RabbitMQProducer {

     @Autowired
    RabbitTemplate rabbitTemplate;

     @PostConstruct
     private void init(){
         rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){

             @Override
             //和交换机确认
             public void confirm(CorrelationData correlationData, boolean b, String s) {
                 String id = correlationData != null ? correlationData.getId() :"";
                 if (b){
                     System.out.println("交换机收到消息Id------"+id);
                 }else {
                     System.out.println("交换机未收到消息Id========"+id+"原因"+s);
                 }
             }
         });
         rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback(){
             @Override
             //路由退回
             public void returnedMessage(ReturnedMessage returnedMessage) {
                 System.out.println("消息："+new String(returnedMessage.getMessage().getBody()));
                 System.out.println("交换机："+returnedMessage.getExchange());
                 System.out.println("退回原因："+returnedMessage.getReplyText());
                 System.out.println("路由key："+returnedMessage.getRoutingKey());
             }
         });
     }

    //Direct模式，支持手动应答和消息确认
    public void direct()   {

            for(int i=0;i<4;i++){
                String id = UUID.randomUUID().toString();
                String messageData = "test message, hello!";
                String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Map<String,Object> map=new HashMap<>();
                map.put("id",id);
                map.put("messageData",messageData);
                map.put("createTime",createTime);
                CorrelationData  correlationData= new CorrelationData("direct"+i);
                //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
                rabbitTemplate.convertSendAndReceive("TestDirectExchange", "TestDirectRouting", map,correlationData);
            }
    }
    //发布订阅模式
    public void publish() throws Exception {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "publish ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        CorrelationData  correlationData= new CorrelationData("publish");
        rabbitTemplate.convertSendAndReceive("fanout_exchange", "", manMap,correlationData);

    }
    //topic模式 路由匹配
    public void topIc() throws Exception {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "topIc ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        CorrelationData  correlationData= new CorrelationData("topic.man");
        CorrelationData  correlationData2= new CorrelationData("topic.man.xxl");
        rabbitTemplate.convertSendAndReceive("topic_exchange", "topic.man", manMap,correlationData);
        rabbitTemplate.convertSendAndReceive("topic_exchange", "topic.man.xxl", manMap,correlationData2);

    }
    //work 生产者，消费者模式
    public void work() throws Exception {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "work ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertSendAndReceive("rabbit_work_queue",manMap);
    }
    //队列已满，进入死信
    public void normal() throws Exception {
        for(int i=0;i<4;i++){
            String messageId = String.valueOf(UUID.randomUUID());

            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> manMap = new HashMap<>();
            manMap.put("messageId", messageId);
            manMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend("directExchange", "normal.key", manMap);
        }
    }
    //消息过期，进入死信
    public void ttl() throws Exception {
            String messageId = String.valueOf(UUID.randomUUID());
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> manMap = new HashMap<>();
            manMap.put("messageId", messageId);
            manMap.put("createTime", createTime);
            rabbitTemplate.convertSendAndReceive("directExchange","ttl.key",manMap);
    }


    public void bcckUp() throws Exception {
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", "1");
        CorrelationData  correlationData= new CorrelationData("bcckUp1");
        rabbitTemplate.convertSendAndReceive("confirmExchange","confirm.key",manMap,correlationData);
        CorrelationData  correlationData2= new CorrelationData("bcckUp2");
        manMap.put("messageId", "2");
        rabbitTemplate.convertSendAndReceive("confirmExchange","ll.key",manMap,correlationData2);

     }
}
