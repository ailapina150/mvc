package com.resume.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {
    public static final String MAIL_QUEUE = "mailQueue";
    public static final String MAIL_EXCHANGE = "mailExchange";
    public static final String MAIL_ROUTING_KEY = "mail.routing.key";

    public static final String PROJECT_QUEUE = "projectQueue";
    public static final String PROJECT_EXCHANGE = "projectExchange";
    public static final String PROJECT_ROUTING_KEY = "project.routing.key";

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE, false);
    }

    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding mailBinding(Queue mailQueue, DirectExchange mailExchange) {
        return BindingBuilder.bind(mailQueue)
                .to(mailExchange)
                .with(MAIL_ROUTING_KEY);
    }

    @Bean
    public Queue projectQueue() {
        return new Queue(PROJECT_QUEUE, false);
    }

    @Bean
    public DirectExchange projectExchange() {
        return new DirectExchange(PROJECT_EXCHANGE, true, false);
    }

    @Bean
    public Binding projectBinding(Queue projectQueue, DirectExchange mailExchange) {
        return BindingBuilder.bind(projectQueue)
                .to(mailExchange)
                .with(PROJECT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // ВАЖНО: Настройте RabbitTemplate с JSON конвертером
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
