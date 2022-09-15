package com.geniusee.cinema.dao;

import com.geniusee.cinema.dao.implementation.DAOFactoryImpl;

public interface DAOFactory {

    MovieDAO getMovieDAO();

    OrderDAO getOrderDAO();

    static DAOFactory getInstance() {
        return new DAOFactoryImpl();
    }

}
