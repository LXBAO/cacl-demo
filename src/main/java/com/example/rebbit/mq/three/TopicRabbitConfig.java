package com.example.rebbit.mq.three;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author lx
 * @data 2022/11/1 9:07 topic 模式
 */
@Configuration
public class TopicRabbitConfig {
    @Bean
    public Queue topicQueueOne() {
        return new Queue("rabbit_topic_queue_1",true);
    }
    @Bean
    public Queue topicQueueOTwo() {
        return new Queue("rabbit_topic_queue_2",true);
    }
    /**
     * 定义 TopicExchange 类型交换机
     * @return
     */
    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange("topic_exchange");
    }

    //" * " 与 " # " ，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）
    /**
     * 队列一绑定到交换机 且设置路由键为 topic.#
     * @return
     */
    @Bean
    public Binding bindingTopic1() {
        return BindingBuilder.bind(topicQueueOne()).to(exchangeTopic()).with("topic.#");
    }
    /**
     * 队列一绑定到交换机 且设置路由键为 topic.*
     * @return
     */
    @Bean
    public Binding bindingTopic2() {
        return BindingBuilder.bind(topicQueueOTwo()).to(exchangeTopic()).with("topic.*");
    }
}
