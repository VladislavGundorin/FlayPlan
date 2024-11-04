package org.example.flayplan.messaging;

import org.example.flayplan.conf.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = RabbitMQConfig.EXCHANGE_NAME;
    }

    public void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
