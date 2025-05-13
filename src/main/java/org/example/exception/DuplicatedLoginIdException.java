package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedLoginIdException extends RuntimeException {
    public DuplicatedLoginIdException(String message){
        super(message);
    }
}
