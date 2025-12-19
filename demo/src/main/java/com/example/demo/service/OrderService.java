package com.example.demo.service;

import com.example.demo.model.request.OrderRequest;
import com.example.demo.model.response.OrderResponse;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository repo;

    // 1️⃣ Create Order
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Generate order ID
        String orderId = UUID.randomUUID().toString();
        orderRequest.setOrderID(orderId);
        OrderRequest saved =repo.save(orderRequest);

        OrderResponse response = new OrderResponse();
        response.setOrderId(saved.getOrderID());
        response.setStatus("CREATED");
        response.setMessage("Order received and processing started");

        // ✅ Here, later we will trigger async tasks like payment, inventory, notification

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