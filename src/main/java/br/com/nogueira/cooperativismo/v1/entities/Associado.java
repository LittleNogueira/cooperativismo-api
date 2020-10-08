package br.com.nogueira.cooperativismo.v1.entities;

import br.com.nogueira.cooperativismo.v1.enums.StatusEnum;

import javax.persistence.*;

@Entity
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    private StatusEnum status;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Boolean isAptoParaVotar(){
        return status.equals(StatusEnum.ABLE_TO_VOTE);
    }
}
