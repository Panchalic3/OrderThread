package com.example.demo.process;

import com.example.demo.model.request.OrderRequest;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class OrderProcess {

    @Autowired
    OrderRepository orderRepository;

    public void processOrder(String orderId) {
        processPayment(orderId);
        updateInventory(orderId);
        sendNotification(orderId);
    }

    private void processPayment(String orderId) {
        updateStatus(orderId, "PAYMENT_SUCCESS", "Payment processed");
    }

    private void updateInventory(String orderId) {
        updateStatus(orderId, "INVENTORY_UPDATED", "Inventory updated");
    }

    private void sendNotification(String orderId) {
        updateStatus(orderId, "COMPLETED", "Order completed");
    }

    private void updateStatus(String orderId, String status, String message) {
        Optional<OrderRequest> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            OrderRequest order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}

