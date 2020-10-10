package br.com.nogueira.cooperativismo.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<Voto> votos = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime dataHoraFinalizacao;

    public Long getId() {
        return id;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public LocalDateTime getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }

    @PrePersist
    public void prePersist(){
        if(Objects.isNull(dataHoraFinalizacao)){
            this.dataHoraFinalizacao = dataHoraCriacao.plusMinutes(1);
        }
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "id=" + id +
                ", votos=" + votos +
                ", dataHoraCriacao=" + dataHoraCriacao +
                ", dataHoraFinalizacao=" + dataHoraFinalizacao +
                '}';
    }
}

