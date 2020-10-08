package br.com.nogueira.cooperativismo.v1.entities;

import br.com.nogueira.cooperativismo.v1.enums.VotoEnum;

import javax.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VotoEnum voto;

    @OneToOne
    private Associado associado;

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
}
