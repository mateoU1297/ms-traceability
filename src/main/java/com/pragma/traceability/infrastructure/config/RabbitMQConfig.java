package com.pragma.traceability.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_STATUS_CHANGED_QUEUE = "order.status.changed.queue";
    public static final String ORDER_STATUS_CHANGED_DLQ = "order.status.changed.dlq";

    @Bean
    public Queue orderStatusChangedQueue() {
        return QueueBuilder.durable(ORDER_STATUS_CHANGED_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", ORDER_STATUS_CHANGED_DLQ)
                .build();
    }

    @Bean
    public Queue orderStatusChangedDlq() {
        return QueueBuilder.durable(ORDER_STATUS_CHANGED_DLQ).build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}