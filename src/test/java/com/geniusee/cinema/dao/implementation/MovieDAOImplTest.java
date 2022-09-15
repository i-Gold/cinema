package com.geniusee.cinema.dao.implementation;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovieDAOImplTest {

    private static final Long TEST_ID = 1L;
    private Movie expected;
    private Movie updated;

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieDAOImpl movieDAO;

    @BeforeEach
    void setup() {
        this.expected = getTestMovie();
        this.updated = getUpdatedMovie();
    }

    @Test
    void add() {
        when(movieRepository.save(expected)).thenReturn(expected);
        when(movieRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));

        Movie actual = movieDAO.add(expected);

        verify(movieRepository).save(expected);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void update() {
        when(movieRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));
        when(movieRepository.save(updated)).thenReturn(updated);

        Movie actual = movieDAO.update(updated);

        verify(movieRepository).findById(TEST_ID);
        verify(movieRepository).save(updated);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(updated).isEqualTo(actual);
    }

    @Test
    void deleteById() {
        when(movieRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));
        doNothing().when(movieRepository).delete(expected);
        boolean expectedResult = true;

        boolean actual = movieDAO.deleteById(TEST_ID);

        verify(movieRepository).delete(expected);
        Assertions.assertThat(expectedResult).isEqualTo(actual);
    }

    @Test
    void findById() {
        when(movieRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));

        Movie actual = movieDAO.findById(TEST_ID)
                .orElse(null);

        verify(movieRepository).findById(TEST_ID);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getAll() {
        when(movieRepository.findAll()).thenReturn(getListOfMovies());

        List<Movie> actual = movieDAO.getAll();

        verify(movieRepository).findAll();
        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(2).isEqualTo(actual.size());
        Assertions.assertThat(actual.get(0).getName()).isEqualTo(getTestMovie().getName());
        Assertions.assertThat(actual.get(1).getName()).isEqualTo("Matrix: Resurrection");
    }

    private Movie getTestMovie() {
        Movie movie = new Movie();
        movie.setId(TEST_ID);
        movie.setName("Thor: Love and Thunder");
        movie.setDescription("American superhero film based on the Marvel comics character");
        return movie;
    }

    private Movie getUpdatedMovie() {
        Movie movie = new Movie();
        movie.setId(TEST_ID);
        movie.setName("Thor: Love and Thunder");
        movie.setDescription("New Marvel chapter about superhero Thor");
        return movie;
    }

    private List<Movie> getListOfMovies() {
        List<Movie> result = new ArrayList<>();

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setName("Matrix: Resurrection");
        movie2.setDescription("The long-awaited return of the fourth installment in the beloved Lana Wachowski franchise. The new Matrix will reunite Keanu Reeves and Carrie-Anne Moss as Neo and Trinity. Their meeting will take place again as for the first time!");

        result.add(getTestMovie());
        result.add(movie2);

        return result;
    }
    
}