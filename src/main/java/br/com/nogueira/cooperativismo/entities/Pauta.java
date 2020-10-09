package br.com.nogueira.cooperativismo.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Pauta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    private Sessao sessao;

    @OneToOne(cascade = CascadeType.ALL)
    private Resultado resultado;

    @CreationTimestamp
    private LocalDateTime dataHoraCriacao;

    @UpdateTimestamp
    private LocalDateTime dataHoraAtualizacao;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public LocalDateTime getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

}
