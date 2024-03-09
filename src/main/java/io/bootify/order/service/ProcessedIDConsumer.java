package io.bootify.order.service;

import io.bootify.order.controller.OrderController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessedIDConsumer {
    private final OrderController orderController;

    public ProcessedIDConsumer(OrderController orderController) {
        this.orderController = orderController;
    }

    @KafkaListener(topics = "processing", groupId = "group-two")
    public void consumeProcessedID(Long id) {
        try {
            System.out.println("Consumed processed ID: " + id);
            orderController.updateProcessed(id);
        } catch (Exception e) {
            // You can rethrow the exception or handle it based on your requirements
            throw new RuntimeException("Failed to process processedID", e);
        }
    }
}