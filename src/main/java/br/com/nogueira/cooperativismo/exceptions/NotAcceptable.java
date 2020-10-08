package br.com.nogueira.cooperativismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptable extends RuntimeException {
    public NotAcceptable(String message){
        super(message);
    }
}
