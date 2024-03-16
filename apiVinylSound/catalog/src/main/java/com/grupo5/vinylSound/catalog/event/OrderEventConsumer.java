package com.grupo5.vinylSound.catalog.event;

import com.grupo5.vinylSound.catalog.config.rabbitMQ.RabbitMQConfig;
import com.grupo5.vinylSound.catalog.model.dto.order.ProductOrderResponseDTO;
import com.grupo5.vinylSound.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final ProductService productService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER)
    public void listen(List<ProductOrderResponseDTO> products){
        productService.increaseSell(products);
    }
}
