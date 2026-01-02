package com.example.demo.process;

import com.example.demo.model.request.OrderRequest;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
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
        retryUpdate(orderId, "PAYMENT");
    }

    private void updateInventory(String orderId) {
        retryUpdate(orderId, "INVENTORY");
    }

    private void sendNotification(String orderId) {
        retryUpdate(orderId, "NOTIFICATION");
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

    //TASK 2.3: LOST UPDATE PROBLEM - added for optimistic locking
    private void retryUpdate(String orderId, String type) {
        boolean updated = false;
        while (!updated) {
            try {
                OrderRequest order = orderRepository.findById(orderId).get();
                switch (type) {
                    case "PAYMENT" -> order.setPaymentDone(true);
                    case "INVENTORY" -> order.setInventoryDone(true);
                    case "NOTIFICATION" -> order.setNotificationDone(true);
                }
                orderRepository.save(order);
                updated = true;

                checkAndMarkCompleted(orderId);

            } catch (OptimisticLockingFailureException e) {
                // another thread updated first → retry
            }
        }
    }
}

