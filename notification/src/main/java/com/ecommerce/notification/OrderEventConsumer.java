package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import java.util.function.Consumer;


@Configuration
public class OrderEventConsumer {
    private static final Logger log = LogManager.getLogger(OrderEventConsumer.class);

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated(){
        return event -> {
            log.info("Received order created event for order: {}", event.getOrderId());
            log.info("Received order created event for order: {}", event.getUserId());
        };
    }
}
