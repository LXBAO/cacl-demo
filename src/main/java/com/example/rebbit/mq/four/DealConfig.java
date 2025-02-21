package com.example.rebbit.mq.four;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/1 16:36
 */
//模拟死信队列
@Configuration
public class DealConfig {
    @Bean
    public Queue normalQueue() {
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange：这里声明当前业务队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "deadDirectExchange");
        // x-dead-letter-routing-key：这里声明当前业务队列的死信路由 key
        args.put("x-dead-letter-routing-key", "dead.key");
        //最大队列数
        args.put("x-max-length",2);
        return new Queue("normalQueue", true, false, false, args);
    }

    @Bean
    public Queue ttlQueue() {
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange：这里声明当前业务队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "deadDirectExchange");
        // x-dead-letter-routing-key：这里声明当前业务队列的死信路由 key
        args.put("x-dead-letter-routing-key", "dead.key");
        //消息超时时间(毫秒)
        args.put("x-message-ttl",6000);
        return new Queue("ttlQueue", true, false, false, args);
    }



    // 业务队列的交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    // 业务队列 A 与交换机绑定，并指定 Routing_Key
    @Bean
    public Binding businessBindingA() {
        return BindingBuilder.bind(normalQueue()).to(directExchange()).with("normal.key");
    }
    @Bean
    public Binding businessBindingB() {
        return BindingBuilder.bind(ttlQueue()).to(directExchange()).with("ttl.key");
    }


    // --------------------------死信队列--------------------------
    // 死信队列 A
    @Bean
    public Queue deadQueue() {
        return new Queue("deadQueue");
    }

    // 死信交换机
    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange("deadDirectExchange");
    }

    // 死信队列 A 与死信交换机绑定，并指定 Routing_Key
    @Bean
    public Binding deadLetterBindingA() {
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with("dead.key");
    }

   /* @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        // 创建死信交换机和对列
        rabbitAdmin.declareExchange(deadDirectExchange());
        rabbitAdmin.declareQueue(deadQueue());
        // 创建业务交换机和对列
        rabbitAdmin.declareExchange(directExchange());
        rabbitAdmin.declareQueue(normalQueue());
        rabbitAdmin.declareQueue(ttlQueue());
        return rabbitAdmin;
    }
*/
}
