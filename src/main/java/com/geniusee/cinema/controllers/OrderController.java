package com.geniusee.cinema.controllers;

import com.geniusee.cinema.models.orders.Order;
import com.geniusee.cinema.models.orders.OrderRequest;
import com.geniusee.cinema.services.orders.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/cinema")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.saveOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<Order> findOrderById(@PathVariable("id") long id) {
        return new ResponseEntity<>(orderService.getOrderByID(id), HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.updateOrder(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        orderService.deleteOrderByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
