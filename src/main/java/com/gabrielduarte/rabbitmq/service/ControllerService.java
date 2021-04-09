package com.gabrielduarte.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ControllerService {

    private final RabbitTemplate rabbitTemplate;

    public ControllerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce() {
        double random = Math.random();

        rabbitTemplate.convertAndSend("simple-exchange", "simple-exchange", "" + random);
        System.out.println(random);
    }

    @RabbitListener(queues = "simple-queue")
    public void consume(Message message) {
        System.out.println("consumed message= " + message);
    }

}
