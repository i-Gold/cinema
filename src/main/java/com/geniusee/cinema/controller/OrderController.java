package com.geniusee.cinema.controller;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.domain.dto.OrderDTO;
import com.geniusee.cinema.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> addNewOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.addNewOrder(orderDTO));
    }

    @PutMapping("/orders")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderDTO));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/price")
    public ResponseEntity<Page<Order>> fetchAllOrdersWherePriceIsBiggerThan(@RequestParam("price") Double price, Integer page, Integer size, Pageable pageable) {
        return ResponseEntity.ok(orderService.fetchAllOrdersWherePriceIsBiggerThan(price, page, size, pageable));
    }

    @GetMapping("/orders/hall")
    public ResponseEntity<Page<Order>> fetchOrdersWhereHallIs(@RequestParam("hall") Integer hall, Pageable pageable) {
        return ResponseEntity.ok(orderService.fetchOrdersWhereHallIs(hall, pageable));
    }

}
