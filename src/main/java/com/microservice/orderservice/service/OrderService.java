package com.microservice.orderservice.service;

import com.microservice.orderservice.entity.*;
import com.microservice.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest  ) {
        Order order = orderRequest.getOrder();
        order = orderRepository.save(order);

        PaymentRequest payment = new PaymentRequest();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice() * order.getQuantity());
        
        PaymentResponse paymentResponse = restTemplate.postForObject("http://payment-service:9191/payment",
                payment, PaymentResponse.class);

        String success = paymentResponse.getPaymentStatus().equals("SUCCESS") ? "Successfully placed the order"
                : "Order placed but payment got failed.";

        OrderResponse response = new OrderResponse();
        response.setOrder(order);
        response.setStatus(success);
        response.setAmount(paymentResponse.getAmount());
        response.setTransactionId(paymentResponse.getTransactionId());

        return response;
    }
}
