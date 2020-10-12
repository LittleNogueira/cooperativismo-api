package br.com.nogueira.cooperativismo.dtos;

import br.com.nogueira.cooperativismo.enums.StatusTicketEnum;

public class TicketDto {

    private Long id;

    private StatusTicketEnum status;

    private VotoDto votoDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusTicketEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTicketEnum status) {
        this.status = status;
    }

    public VotoDto getVotoDto() {
        return votoDto;
    }

    public void setVotoDto(VotoDto votoDto) {
        this.votoDto = votoDto;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + id +
                ", status=" + status +
                ", votoDto=" + votoDto +
                '}';
    }
}
