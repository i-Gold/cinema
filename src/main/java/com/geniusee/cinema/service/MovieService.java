package com.geniusee.cinema.service;

import com.geniusee.cinema.converter.MovieConverter;
import com.geniusee.cinema.dao.DAOFactory;
import com.geniusee.cinema.dao.MovieDAO;
import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.geniusee.cinema.converter.MovieConverter.fromDTO;
import static com.geniusee.cinema.converter.MovieConverter.toDTO;

@Service
@Transactional
public class MovieService {

    private final MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();

    public MovieDTO addNewMovie(MovieDTO movieDTO) {
        return toDTO(movieDAO.add(fromDTO(movieDTO)));
    }

    public MovieDTO updateMovie(MovieDTO movieDTO) {
        return toDTO(movieDAO.update(fromDTO(movieDTO)));
    }

    public boolean deleteMovie(Long id) {
        return movieDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MovieDTO findMovieById(Long id) {
        Optional<Movie> movie = movieDAO.findById(id);
        return movie.map(MovieConverter::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<MovieDTO> getAllMovies() {
        return movieDAO.getAll().stream()
                .filter(Objects::nonNull)
                .map(MovieConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Movie> fetchMoviesWhereDescriptionContains(String description, Pageable pageable) {
        return movieDAO.fetchMoviesWhereDescriptionContains(description, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Movie> fetchAllMoviesWhereNameStartsWith(String name, Integer page, Integer size, Pageable pageable) {
        return movieDAO.fetchAllMoviesWhereNameStartsWith(name, page, size, pageable);
    }

}
