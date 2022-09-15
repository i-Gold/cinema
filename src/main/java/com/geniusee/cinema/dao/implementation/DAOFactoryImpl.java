package com.geniusee.cinema.dao.implementation;

import com.geniusee.cinema.dao.DAOFactory;
import com.geniusee.cinema.dao.MovieDAO;
import com.geniusee.cinema.dao.OrderDAO;
import com.geniusee.cinema.repository.MovieRepository;
import com.geniusee.cinema.repository.OrderRepository;
import lombok.Setter;

@Setter
public class DAOFactoryImpl implements DAOFactory {

    private MovieRepository movieRepository;
    private OrderRepository orderRepository;

    @Override
    public MovieDAO getMovieDAO() {
        return new MovieDAOImpl(movieRepository);
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl(orderRepository);
    }

}
