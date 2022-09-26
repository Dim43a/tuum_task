package com.example.tuum_task.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("test-exchange");
    }

    @Bean
    Binding testBinding(Queue testQueue, DirectExchange exchange) {
        return BindingBuilder.bind(testQueue).to(exchange).with("test-routing-key");
    }
}
