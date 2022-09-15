package com.geniusee.cinema.converter;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.dto.MovieDTO;

public class MovieConverter {

    public static Movie fromDTO(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .name(movieDTO.getName())
                .description(movieDTO.getDescription())
                .build();
    }

    public static MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .build();
    }

}
