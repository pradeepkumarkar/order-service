package com.microservice.orderservice.controller;

import com.microservice.orderservice.entity.OrderRequest;
import com.microservice.orderservice.entity.OrderResponse;
import com.microservice.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse bookOrder(@RequestBody OrderRequest orderRequest){
        return orderService.createOrder(orderRequest);

    }
}
