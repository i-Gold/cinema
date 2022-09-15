package com.geniusee.cinema.dao.implementation;

import com.geniusee.cinema.dao.MovieDAO;
import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.exception.EntityIsNotFoundException;
import com.geniusee.cinema.repository.MovieRepository;
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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovieDAOImpl implements MovieDAO {
    private static final Logger logger = Logger.getLogger(MovieDAOImpl.class);
    private final MovieRepository movieRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MovieDAOImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie add(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Movie movie) {
        Long movieId = movie.getId();
        Movie result = findById(movieId)
                .orElse(null);
        if (result == null) {
            final String errorMessage = "Movie with id '" + movieId + "' not found";
            logger.error(errorMessage);
            throw new EntityIsNotFoundException(errorMessage);
        }
        result.setName(movie.getName());
        result.setDescription(movie.getDescription());
        return movieRepository.save(result);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Movie> result = findById(id);
        if (result.isEmpty()) {
            final String errorMessage = "Movie with id '" + id + "' not found";
            logger.error(errorMessage);
            return false;
        }
        movieRepository.delete(result.get());
        return true;
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Page<Movie> fetchMoviesWhereDescriptionContains(String description, Pageable pageable) {
        List<Movie> result = movieRepository.findAll(descriptionContains(description));
        return new PageImpl<>(result, pageable, result.size());
    }

    private static Specification<Movie> descriptionContains(String description) {
        return (movie, cq, cb) -> cb.like(movie.get("description"), "%" + description + "%");
    }

    @Override
    public Page<Movie> fetchAllMoviesWhereNameStartsWith(String name, Integer page, Integer size, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = builder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        List<Predicate> predicates = fillPredicates(builder, root, name);
        countWithCriteria(builder, criteriaQuery, name);
        Order order = builder.asc(root.get("id"));

        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(order);

        return createQueryWithPagination(criteriaQuery, page, size, pageable);
    }

    private List<Predicate> fillPredicates(CriteriaBuilder builder, Root<Movie> root, String name) {
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(builder.like(root.get("name"), name + "%"));
        }
        return predicates;
    }

    private void countWithCriteria(CriteriaBuilder builder, CriteriaQuery<Movie> criteriaQuery, String name) {
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Movie> rootCount = countCriteria.from(criteriaQuery.getResultType());
        countCriteria.select(builder.count(rootCount));
        List<Predicate> predicates = fillPredicates(builder, rootCount, name);
        countCriteria.where(predicates.toArray(new Predicate[]{}));
        entityManager.createQuery(countCriteria).getSingleResult();
    }

    private Page<Movie> createQueryWithPagination(CriteriaQuery<Movie> criteriaQuery, Integer page, Integer size, Pageable pageable) {
        TypedQuery<Movie> typedQuery = entityManager.createQuery(criteriaQuery);
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
