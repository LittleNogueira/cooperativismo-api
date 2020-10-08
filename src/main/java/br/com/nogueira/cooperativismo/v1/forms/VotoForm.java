package br.com.nogueira.cooperativismo.v1.forms;

import br.com.nogueira.cooperativismo.v1.enums.VotoEnum;

public class VotoForm {

    private Long idAssociado;

    private VotoEnum voto;

    public Long getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(Long idAssociado) {
        this.idAssociado = idAssociado;
    }

    public VotoEnum getVoto() {
        return voto;
    }

    public void setVoto(VotoEnum voto) {
        this.voto = voto;
    }
}
