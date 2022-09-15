package com.geniusee.cinema.dao.implementation;

import com.geniusee.cinema.dao.OrderDAO;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.exception.EntityIsNotFoundException;
import com.geniusee.cinema.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAOImpl implements OrderDAO {

    private static final Logger logger = Logger.getLogger(OrderDAO.class);
    private final OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order add(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        final Long orderId = order.getId();
        Order result = findById(orderId)
                .orElse(null);
        if (result == null) {
            final String errorMessage = "Order with id '" + orderId + "' not found";
            logger.error(errorMessage);
            throw new EntityIsNotFoundException(errorMessage);
        }
        result.setDate(order.getDate());
        result.setRow(order.getRow());
        result.setPlace(order.getPlace());
        result.setHall(order.getHall());
        result.setPrice(order.getPrice());
        result.setMovie(order.getMovie());
        return orderRepository.save(result);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Order> result = findById(id);
        if (result.isEmpty()) {
            final String errorMessage = "Order with id '" + id + "' not found";
            logger.error(errorMessage);
            return false;
        }
        orderRepository.delete(result.get());
        return true;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> fetchOrdersWhereHallIs(Integer hall, Pageable pageable) {
        List<Order> result = orderRepository.findAll(hallIs(hall));
        return new PageImpl<>(result, pageable, result.size());
    }

    private static Specification<Order> hallIs(Integer hall) {
        return (order, cq, cb) -> cb.equal(order.get("hall"), hall);
    }

    @Override
    public Page<Order> fetchAllOrdersWherePriceIsBiggerThan(Double price, Integer page, Integer size, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        List<Predicate> predicates = fillPredicates(builder, root, price);
        countWithCriteria(builder, criteriaQuery, price);
        javax.persistence.criteria.Order order = builder.desc(root.get("date"));

        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(order);

        return createQueryWithPagination(criteriaQuery, page, size, pageable);
    }

    private List<Predicate> fillPredicates(CriteriaBuilder builder, Root<Order> root, Double price) {
        List<Predicate> predicates = new ArrayList<>();
        if (price != null) {
            predicates.add(builder.greaterThan(root.get("price"), price));
        }
        return predicates;
    }

    private void countWithCriteria(CriteriaBuilder builder, CriteriaQuery<Order> criteriaQuery, Double price) {
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Order> rootCount = countCriteria.from(criteriaQuery.getResultType());
        countCriteria.select(builder.count(rootCount));
        List<Predicate> predicates = fillPredicates(builder, rootCount, price);
        countCriteria.where(predicates.toArray(new Predicate[]{}));
        entityManager.createQuery(countCriteria).getSingleResult();
    }

    private Page<Order> createQueryWithPagination(CriteriaQuery<Order> criteriaQuery, Integer page, Integer size, Pageable pageable) {
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        int totalItems = typedQuery.getResultList().size();
        if (page == null && size == null) {
            typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            typedQuery.setMaxResults(pageable.getPageSize());
        }
        if (page != null && size == null) {
            typedQuery.setFirstResult(page * pageable.getPageSize());
            typedQuery.setMaxResults(pageable.getPageSize());
        }
        if (page == null && size != null) {
            typedQuery.setFirstResult(pageable.getPageNumber() * size);
            typedQuery.setMaxResults(size);
        }
        if (page != null && size != null) {
            typedQuery.setFirstResult(page * size);
            typedQuery.setMaxResults(size);
        }
        return new PageImpl<>(typedQuery.getResultList(), pageable, totalItems);
    }

}
