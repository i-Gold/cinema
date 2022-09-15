package com.geniusee.cinema.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 3999070536313457807L;

    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;

    private Integer row;

    private Integer place;

    private Integer hall;

    private Double price;

    private Long movieId;
}
