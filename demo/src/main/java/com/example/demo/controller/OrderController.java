package com.example.demo.controller;


import com.example.demo.model.request.OrderRequest;
import com.example.demo.model.response.OrderResponse;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService service) {
        this.orderService = service;
    }

    @PostMapping("/placeOrder")
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) {
        // Calls service to handle order
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderRequest getOrderStatus(@PathVariable String orderId) {
        return orderService.getOrderStatus(orderId);
    }

//    @DeleteMapping("/{orderId}")
//    public OrderResponse cancelOrder(@PathVariable String orderId) {
//        return orderService.cancelOrder(orderId);
//    }
}
