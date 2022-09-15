package com.geniusee.cinema.exception;

import org.springframework.http.HttpStatus;

public interface ErrorWrapper {

    String getMessage();

    HttpStatus getStatus();
}
