package com.geniusee.cinema.dao.implementation;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.domain.Order;
import com.geniusee.cinema.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderDAOImplTest {

    private static final Long TEST_ID = 1L;
    private Order expected;
    private Order updated;

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderDAOImpl orderDAO;

    @BeforeEach
    void setup() {
        this.expected = getTestOrder();
        this.updated = getUpdatedOrder();
    }

    @Test
    void add() {
        when(orderRepository.save(expected)).thenReturn(expected);
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));

        Order actual = orderDAO.add(expected);

        verify(orderRepository).save(expected);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void update() {
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));
        when(orderRepository.save(updated)).thenReturn(updated);

        Order actual = orderDAO.update(updated);

        verify(orderRepository).findById(TEST_ID);
        verify(orderRepository).save(updated);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(updated).isEqualTo(actual);
    }

    @Test
    void deleteById() {
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));
        doNothing().when(orderRepository).delete(expected);
        boolean expectedResult = true;

        boolean actual = orderDAO.deleteById(TEST_ID);

        verify(orderRepository).delete(expected);
        Assertions.assertThat(expectedResult).isEqualTo(actual);
    }

    @Test
    void findById() {
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.of(expected));

        Order actual = orderDAO.findById(TEST_ID)
                .orElse(null);

        verify(orderRepository).findById(TEST_ID);
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getAll() {
        when(orderRepository.findAll()).thenReturn(getListOfOrders());

        List<Order> actual = orderDAO.getAll();

        verify(orderRepository).findAll();
        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(2).isEqualTo(actual.size());
        Assertions.assertThat(actual.get(0).getMovie().getName()).isEqualTo(getTestMovie().getName());
        Assertions.assertThat(actual.get(1).getMovie().getName()).isEqualTo("Matrix: Resurrection");
    }

    private Order getTestOrder() {
        Order order = new Order();
        order.setId(TEST_ID);
        order.setMovie(getTestMovie());

        String stringDate = "20-09-2022 20:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(stringDate, formatter);

        order.setDate(date);
        order.setRow(7);
        order.setPlace(15);
        order.setHall(3);
        order.setPrice(100.0d);
        return order;
    }

    private Order getUpdatedOrder() {
        Order order = new Order();
        order.setId(TEST_ID);

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setName("Matrix: Resurrection");
        movie2.setDescription("The long-awaited return of the fourth installment in the beloved Lana Wachowski franchise. The new Matrix will reunite Keanu Reeves and Carrie-Anne Moss as Neo and Trinity. Their meeting will take place again as for the first time!");

        order.setMovie(movie2);

        String stringDate = "25-09-2022 18:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(stringDate, formatter);

        order.setDate(date);
        order.setRow(7);
        order.setPlace(15);
        order.setHall(3);
        order.setPrice(100.0d);
        return order;
    }

    private List<Order> getListOfOrders() {
        List<Order> result = new ArrayList<>();

        Order order2 = new Order();
        order2.setId(2L);

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setName("Matrix: Resurrection");
        movie2.setDescription("The long-awaited return of the fourth installment in the beloved Lana Wachowski franchise. The new Matrix will reunite Keanu Reeves and Carrie-Anne Moss as Neo and Trinity. Their meeting will take place again as for the first time!");

        order2.setMovie(movie2);

        String stringDate = "25-09-2022 18:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(stringDate, formatter);

        order2.setDate(date);
        order2.setRow(7);
        order2.setPlace(15);
        order2.setHall(3);
        order2.setPrice(100.0d);

        result.add(getTestOrder());
        result.add(order2);

        return result;
    }

    private Movie getTestMovie() {
        Movie movie = new Movie();
        movie.setId(TEST_ID);
        movie.setName("Thor: Love and Thunder");
        movie.setDescription("American superhero film based on the Marvel comics character");
        return movie;
    }

}