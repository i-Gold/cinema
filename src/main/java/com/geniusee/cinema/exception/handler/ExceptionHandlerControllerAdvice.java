package com.geniusee.cinema.exception.handler;

import com.geniusee.cinema.exception.ErrorResponse;
import com.geniusee.cinema.exception.ErrorWrapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice<T extends Throwable> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleException(HttpServletRequest httpServletRequest, T ex) throws T {
        logger.error(httpServletRequest, ex);
        ErrorResponse errorResponse = new ErrorResponse(ex, httpServletRequest);
        if (ex instanceof ErrorWrapper) {
            ErrorWrapper errorWrapper = (ErrorWrapper) ex;
            errorResponse.setStatus(errorWrapper.getStatus().value());
            errorResponse.setMessage(errorWrapper.getMessage());
        }
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatus())).body(errorResponse);
    }


}