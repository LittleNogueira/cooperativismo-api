package br.com.nogueira.cooperativismo.entities;

import br.com.nogueira.cooperativismo.enums.VotoEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VotoEnum voto;

    @OneToOne
    private Associado associado;

    @Column(nullable = false)
    private LocalDateTime dataHoraVotacao;

    public Voto(){}

    public Voto(VotoEnum voto, Associado associado, LocalDateTime dataHoraVotacao){
        this.setVoto(voto);
        this.setAssociado(associado);
        this.setDataHoraVotacao(dataHoraVotacao);
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

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", voto=" + voto +
                ", associado=" + associado +
                ", dataHoraVotacao=" + dataHoraVotacao +
                '}';
    }
}
