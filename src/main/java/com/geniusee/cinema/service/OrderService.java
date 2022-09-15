package com.geniusee.cinema.service;

import com.geniusee.cinema.converter.OrderConverter;
import com.geniusee.cinema.dao.DAOFactory;
import com.geniusee.cinema.dao.OrderDAO;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.domain.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.geniusee.cinema.converter.OrderConverter.fromDTO;
import static com.geniusee.cinema.converter.OrderConverter.toDTO;


@Service
@Transactional
public class OrderService {

    private final OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

    public OrderDTO addNewOrder(OrderDTO orderDTO) {
        return toDTO(orderDAO.add(fromDTO(orderDTO)));
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        return toDTO(orderDAO.update(fromDTO(orderDTO)));
    }

    public boolean deleteOrder(Long id) {
        return orderDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id) {
        Optional<Order> order = orderDAO.findById(id);
        return order.map(OrderConverter::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderDAO.getAll().stream()
                .filter(Objects::nonNull)
                .map(OrderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Order> fetchOrdersWhereHallIs(Integer hall, Pageable pageable) {
        return orderDAO.fetchOrdersWhereHallIs(hall, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Order> fetchAllOrdersWherePriceIsBiggerThan(Double price, Integer page, Integer size, Pageable pageable) {
        return orderDAO.fetchAllOrdersWherePriceIsBiggerThan(price, page, size, pageable);
    }

}
