package com.geniusee.cinema.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class MovieDTO implements Serializable {
    private static final long serialVersionUID = -1348937870945262928L;

    private Long id;

    private String name;

    private String description;

}
