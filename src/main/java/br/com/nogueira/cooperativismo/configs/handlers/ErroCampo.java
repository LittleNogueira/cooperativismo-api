package br.com.nogueira.cooperativismo.configs.handlers;

import org.springframework.validation.FieldError;

public class ErroCampo {
    public String campo;
    public String mensagem;

    public ErroCampo(FieldError fieldError){
        this.campo = fieldError.getField();
        this.mensagem = fieldError.getDefaultMessage();
    }
}
