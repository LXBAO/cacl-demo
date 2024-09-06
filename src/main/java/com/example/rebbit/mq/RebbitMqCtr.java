package com.example.rebbit.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 * @data 2022/10/31 14:27
 */
@RestController
@RequestMapping("/rabbit-mq")
public class RebbitMqCtr {
    @Autowired
    RabbitMQProducer rabbitMQProducer;
    @GetMapping(value = "/direct")
    public void consumer() throws Exception {
        rabbitMQProducer.direct();

    }

    @GetMapping(value = "/topIc")
    public void topIc() throws Exception {
        rabbitMQProducer.topIc();

    }

    @GetMapping(value = "/publish")
    public void publish() throws Exception {
        rabbitMQProducer.publish();

    }

    @GetMapping(value = "/work")
    public void work() throws Exception {
        rabbitMQProducer.work();

    }

    @GetMapping(value = "/normal")
    public void normal() throws Exception {
        rabbitMQProducer.normal();

    }

    @GetMapping(value = "/ttl")
    public void ttl() throws Exception {
        rabbitMQProducer.ttl();

    }

    @GetMapping(value = "/backUp")
    public void backUp() throws Exception {

        rabbitMQProducer.bcckUp();

    }
}
