package br.com.nogueira.cooperativismo.v1.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime dataHoraFinalizacao;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public LocalDateTime getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }
}

