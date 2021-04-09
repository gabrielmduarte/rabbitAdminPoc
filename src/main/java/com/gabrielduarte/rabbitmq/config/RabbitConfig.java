package com.gabrielduarte.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitConfig {

    private final ConnectionFactory connectionFactory;

    public RabbitConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void createRabbitElements() {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        createExchange(admin);
        simpleQueue(admin);
        deadLetterQueue(admin);
    }

    void createExchange(RabbitAdmin rabbitAdmin) {
        Exchange exchange = ExchangeBuilder.directExchange("simple-exchange")
                                        .durable(true)
                                        .build();
        rabbitAdmin.declareExchange(exchange);
    }

    void simpleQueue(RabbitAdmin rabbitAdmin) {
        Queue simpleQueue = QueueBuilder.durable("simple-queue")
                                    .deadLetterExchange("simple-exchange")
                                    .deadLetterRoutingKey("simple-queue.dlq")
                                    .build();

        Binding binding = new Binding("simple-queue",
                                        Binding.DestinationType.QUEUE,
                                        "simple-exchange",
                                        "simple-queue",
                                        null);

        rabbitAdmin.declareQueue(simpleQueue);
        rabbitAdmin.declareBinding(binding);
    }

    void deadLetterQueue(RabbitAdmin rabbitAdmin) {
        Queue dlqQueue = QueueBuilder.durable("simple-queue.dlq")
                                         .build();

        Binding dlqBinding = new Binding("simple-queue.dlq",
                                        Binding.DestinationType.QUEUE,
                                        "simple-exchange",
                                        "simple-queue.dlq",
                                        null);

        rabbitAdmin.declareQueue(dlqQueue);
        rabbitAdmin.declareBinding(dlqBinding);
    }

}
