package br.com.nogueira.cooperativismo.v1.entities;

import br.com.nogueira.cooperativismo.v1.enums.VotoEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VotoEnum voto;

    @OneToOne
    private Associado associado;

    @CreationTimestamp
    private LocalDateTime dataHoraVotacao;

    public Voto(){}

    public Voto(VotoEnum voto, Associado associado){
        this.setVoto(voto);
        this.setAssociado(associado);
    }

    public Long getId() {
        return id;
    }

    public VotoEnum getVoto() {
        return voto;
    }

    public void setVoto(VotoEnum voto) {
        this.voto = voto;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public LocalDateTime getDataHoraVotacao() {
        return dataHoraVotacao;
    }

    public void setDataHoraVotacao(LocalDateTime dataHoraVotacao) {
        this.dataHoraVotacao = dataHoraVotacao;
    }
}
