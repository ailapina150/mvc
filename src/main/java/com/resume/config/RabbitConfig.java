package com.resume.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {
    public static final String MAIL_QUEUE = "mailQueue";
    public static final String EXCHANGE = "mainExchange";
    public static final String MAIL_ROUTING_KEY = "mail.routing.key";

    public static final String PROJECT_QUEUE = "projectQueue";
    public static final String PROJECT_ROUTING_KEY = "project.routing.key";

    // Очередь для почты
    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE, false);
    }

    // Очередь для проектов
    @Bean
    public Queue projectQueue() {
        return new Queue(PROJECT_QUEUE, false);
    }

    // Один общий Exchange для всех сообщений
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    // Привязка почтовой очереди к exchange
    @Bean
    public Binding mailBinding(Queue mailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(mailQueue)
                .to(exchange)
                .with(MAIL_ROUTING_KEY);
    }

    // Привязка очереди проектов к exchange
    @Bean
    public Binding projectBinding(Queue projectQueue, DirectExchange exchange) {
        return BindingBuilder.bind(projectQueue)
                .to(exchange)
                .with(PROJECT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // Дополнительно: настройка контейнера слушателей (опционально)
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
