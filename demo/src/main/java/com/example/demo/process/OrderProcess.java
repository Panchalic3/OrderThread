package com.example.demo.process;

import com.example.demo.model.request.OrderRequest;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Component
public class OrderProcess {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ExecutorService executorService;

    public void processOrder(String orderId) {
        executorService.submit(() -> processPayment(orderId));
        executorService.submit(() -> updateInventory(orderId));
        executorService.submit(() -> sendNotification(orderId));
    }

    private void processPayment(String orderId) {
        OrderRequest order = orderRepository.findById(orderId).get();
        order.setPaymentDone(true);
        orderRepository.save(order);
        checkAndMarkCompleted(orderId);
    }

    private void updateInventory(String orderId) {
        OrderRequest order = orderRepository.findById(orderId).get();
        order.setInventoryDone(true);
        orderRepository.save(order);
        checkAndMarkCompleted(orderId);

    }

    private void sendNotification(String orderId) {
        OrderRequest order = orderRepository.findById(orderId).get();
        order.setNotificationDone(true);
        orderRepository.save(order);
        checkAndMarkCompleted(orderId);
    }

    private synchronized void checkAndMarkCompleted(String orderId) {

        OrderRequest order = orderRepository.findById(orderId).get();

        if (order.getPaymentDone()
                && order.getInventoryDone()
                && order.getNotificationDone()) {

            order.setStatus("COMPLETED");
            orderRepository.save(order);
        }
    }
}

