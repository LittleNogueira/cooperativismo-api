package br.com.nogueira.cooperativismo.dtos;

import br.com.nogueira.cooperativismo.enums.StatusEnum;

public class UserDto {

    private StatusEnum status;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "status=" + status +
                '}';
    }
}
