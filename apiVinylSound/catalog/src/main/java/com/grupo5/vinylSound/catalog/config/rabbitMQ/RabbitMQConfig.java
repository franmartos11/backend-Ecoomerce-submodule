package com.grupo5.vinylSound.catalog.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_CATALOG = "exchangeCatalog";
    public static final String TOPIC_PRODUCT_QUANTITY_SELL = "topicProductQuantitySell";
    public static final String QUEUE_ORDER = "queueOrder";

    @Bean
    public TopicExchange appExchange(){
        return new TopicExchange(EXCHANGE_CATALOG);
    }


    @Bean
    public Queue queueOrder(){return new Queue(QUEUE_ORDER);}

    @Bean
    public Binding declareBindingOrder(){
        return BindingBuilder.bind(queueOrder()).to(appExchange()).with(TOPIC_PRODUCT_QUANTITY_SELL);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}

