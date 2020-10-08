package br.com.nogueira.cooperativismo.dtos;

import br.com.nogueira.cooperativismo.enums.StatusEnum;

public class UserDto {

    private StatusEnum statusEnum;

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
}
