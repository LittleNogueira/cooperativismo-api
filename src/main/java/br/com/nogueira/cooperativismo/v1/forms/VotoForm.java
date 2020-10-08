package br.com.nogueira.cooperativismo.v1.forms;

import br.com.nogueira.cooperativismo.enums.VotoEnum;

import java.time.LocalDateTime;

public class VotoForm {

    private Long idAssociado;

    private VotoEnum voto;

    private LocalDateTime dataHoraVotacao = LocalDateTime.now();

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

    public LocalDateTime getDataHoraVotacao() {
        return dataHoraVotacao;
    }

}
