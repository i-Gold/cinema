package com.geniusee.cinema.controller;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.domain.dto.MovieDTO;
import com.geniusee.cinema.service.MovieService;
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
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> addNewMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addNewMovie(movieDTO));
    }

    @PutMapping("/movies")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieDTO));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Boolean> deleteMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/name")
    public ResponseEntity<Page<Movie>> fetchAllMoviesWhereNameStartsWith(@RequestParam("name") String name, Integer page, Integer size, Pageable pageable) {
        return ResponseEntity.ok(movieService.fetchAllMoviesWhereNameStartsWith(name, page, size, pageable));
    }

    @GetMapping("/movies/description")
    public ResponseEntity<Page<Movie>> fetchMoviesWhereDescriptionContains(@RequestParam("description") String description, Pageable pageable) {
        return ResponseEntity.ok(movieService.fetchMoviesWhereDescriptionContains(description, pageable));
    }

}
