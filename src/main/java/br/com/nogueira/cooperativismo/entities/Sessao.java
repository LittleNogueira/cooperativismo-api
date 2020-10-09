package br.com.nogueira.cooperativismo.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
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

}

