package com.grupo5.vinylSound.order.event;

import com.grupo5.vinylSound.order.config.rabbitMQ.RabbitMQConfig;
import com.grupo5.vinylSound.order.model.dto.order.ProductOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void increaseQuantitySales(List<ProductOrderResponseDTO> products){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CATALOG, RabbitMQConfig.TOPIC_PRODUCT_QUANTITY_SELL,products);
    }
}

