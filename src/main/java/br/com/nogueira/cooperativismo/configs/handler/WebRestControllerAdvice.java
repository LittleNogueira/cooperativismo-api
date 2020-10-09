package br.com.nogueira.cooperativismo.configs.handler;

import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroCampo> handler(MethodArgumentNotValidException exceptions){
        List<ErroCampo> errors = new ArrayList<>();
        return exceptions.getBindingResult().getFieldErrors().stream().map(ErroCampo::new).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Erro handler(NotFoundException e){
        return new Erro(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptable.class)
    public Erro handler(NotAcceptable e){
        return new Erro(e.getMessage());
    }

}
