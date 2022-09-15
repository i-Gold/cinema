package com.geniusee.cinema.converter;

import com.geniusee.cinema.dao.DAOFactory;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.domain.dto.OrderDTO;

import java.util.Objects;

public class OrderConverter {

    public static Order fromDTO(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .date(orderDTO.getDate())
                .row(orderDTO.getRow())
                .place(orderDTO.getPlace())
                .hall(orderDTO.getHall())
                .price(orderDTO.getPrice())
                .movie(orderDTO.getMovieId() != null ?
                        DAOFactory.getInstance()
                                .getMovieDAO().findById(orderDTO.getMovieId())
                                .orElse(null)
                        : null)
                .build();
    }

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .row(order.getRow())
                .place(order.getPlace())
                .hall(order.getHall())
                .price(order.getPrice())
                .movieId(Objects.nonNull(order.getMovie()) ?
                        order.getMovie().getId()
                        : null)
                .build();
    }

}
