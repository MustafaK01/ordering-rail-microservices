package com.mustafak01.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CouldNotCreateException extends RuntimeException {
    public CouldNotCreateException(String message){
        super(message);
    }
}