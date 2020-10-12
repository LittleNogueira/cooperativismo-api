package br.com.nogueira.cooperativismo.entities;

import br.com.nogueira.cooperativismo.enums.StatusTicketEnum;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private StatusTicketEnum status;

    private String motivoErro;

    public Ticket(){
        this.status = StatusTicketEnum.AGUARDANDO;
    }

    public Long getId() {
        return id;
    }

    public StatusTicketEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTicketEnum status) {
        this.status = status;
    }

    public String getMotivoErro() {
        return motivoErro;
    }

    public void setMotivoErro(String motivoErro) {
        this.motivoErro = motivoErro;
    }
}
