package com.mustafak01.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CouldNotFoundProductException extends RuntimeException{
    public CouldNotFoundProductException(){
        super("Couldn't found product");
    }

}
