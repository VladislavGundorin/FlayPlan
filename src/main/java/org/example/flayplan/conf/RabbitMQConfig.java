package org.example.flayplan.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Bean
    public Queue approvalRequestsQueue() {
        return new Queue("approval.requests.queue", true);
    }

    @Bean
    public Queue approvalUpdateQueue() {
        return new Queue("approval.update.queue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding approvalRequestsBinding() {
        return BindingBuilder.bind(approvalRequestsQueue()).to(exchange()).with("approval.requests");
    }

    @Bean
    public Binding approvalUpdateBinding() {
        return BindingBuilder.bind(approvalUpdateQueue()).to(exchange()).with("approval.update");
    }

}
