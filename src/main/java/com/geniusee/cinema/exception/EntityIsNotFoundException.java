package com.geniusee.cinema.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityIsNotFoundException extends RuntimeException implements ErrorWrapper {
    private static final long serialVersionUID = -3974871245414526621L;

    private String message;
    private HttpStatus status =  HttpStatus.FORBIDDEN;

    public EntityIsNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
