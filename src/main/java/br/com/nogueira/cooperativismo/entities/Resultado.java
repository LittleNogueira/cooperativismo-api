package br.com.nogueira.cooperativismo.entities;

import br.com.nogueira.cooperativismo.enums.VotoEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int votosSim;

    private int votosNao;

    private VotoEnum resultado;

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

    public VotoEnum getResultado() {
        return resultado;
    }

    public void setResultado(VotoEnum resultado) {
        this.resultado = resultado;
    }
}
