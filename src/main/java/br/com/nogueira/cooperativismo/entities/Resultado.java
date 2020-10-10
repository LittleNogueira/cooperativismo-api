package br.com.nogueira.cooperativismo.entities;

import br.com.nogueira.cooperativismo.enums.ResultadoEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int votosSim;

    private int votosNao;

    private ResultadoEnum resultado;

    public Long getId() {
        return id;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public void setVotosSim(int votosSim) {
        this.votosSim = votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }

    public void setVotosNao(int votosNao) {
        this.votosNao = votosNao;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoEnum resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "id=" + id +
                ", votosSim=" + votosSim +
                ", votosNao=" + votosNao +
                ", resultado=" + resultado +
                '}';
    }
}
