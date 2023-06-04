package com.mustafak01.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CouldNotUpdateException extends RuntimeException {
    public CouldNotUpdateException(String message){
        super(message);
    }
}
