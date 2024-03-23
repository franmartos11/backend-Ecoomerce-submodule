package com.grupo5.vinylSound.order.event;

import com.grupo5.vinylSound.order.config.rabbitMQ.RabbitMQConfig;
import com.grupo5.vinylSound.order.model.dto.order.ProductOrderResponseDTO;
import com.grupo5.vinylSound.order.model.dto.product.ProductStockRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void increaseQuantitySales(List<ProductOrderResponseDTO> products){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CATALOG, RabbitMQConfig.TOPIC_PRODUCT_QUANTITY_SELL,products);
    }
}

