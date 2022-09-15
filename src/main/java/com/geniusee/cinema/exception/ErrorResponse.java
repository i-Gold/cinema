package com.geniusee.cinema.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 7480926394550877236L;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer status = HttpStatus.BAD_REQUEST.value();
    private String exception;
    private String message;
    private String path;

    public <T extends Throwable> ErrorResponse(T ex, HttpServletRequest httpServletRequest) {
        this.exception = ex.getClass().getName();
        this.message = ex.getMessage();
        this.path = httpServletRequest.getRequestURI();
    }
}
