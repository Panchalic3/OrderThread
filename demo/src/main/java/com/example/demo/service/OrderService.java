package com.example.demo.service;

import com.example.demo.model.request.OrderRequest;
import com.example.demo.model.response.OrderResponse;
import com.example.demo.process.OrderProcess;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class OrderService {

    OrderRepository repo;
    ExecutorService executorService;
    OrderProcess process;

    //  Create Order
    public OrderResponse createOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        orderRequest.setOrderID(orderId);
        orderRequest.setStatus("CREATED");

        //TASK 2.4: FIX RACE CONDITION - Set these false initially
        orderRequest.setPaymentDone(false);
        orderRequest.setInventoryDone(false);
        orderRequest.setNotificationDone(false);

        OrderRequest saved = repo.save(orderRequest);

        OrderResponse response = new OrderResponse();
        response.setOrderId(saved.getOrderID());
        response.setStatus(saved.getStatus());
        response.setMessage("Order received and processing started");

        // 🔥 BACKGROUND PROCESSING STARTS HERE
        executorService.submit(() -> process.processOrder(orderId));
        return response;
    }

    // 2️⃣ Get Order Status
    public OrderRequest getOrderStatus(String orderId) {
        return repo.findById(orderId).orElseGet(null);
    }

    // 3️⃣ Cancel Order (optional)
    public OrderResponse cancelOrder(String orderId) {
        OrderRequest order = repo.findById(orderId).orElseGet(null);
        if (order == null) {
            return new OrderResponse(orderId, "NOT_FOUND", "Order not found");
        }

        order.setStatus("Cancelled");
        return new OrderResponse(orderId, "Cancelled", "Order is cancelled");
    }
}