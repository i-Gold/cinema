package com.geniusee.cinema.dao;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order add(Order order);

    Order update(Order order);

    boolean deleteById(Long id);

    Optional<Order> findById(Long id);

    List<Order> getAll();

    Page<Order> fetchOrdersWhereHallIs(Integer hall, Pageable pageable);

    Page<Order> fetchAllOrdersWherePriceIsBiggerThan(Double price, Integer page, Integer size, Pageable pageable);

}
