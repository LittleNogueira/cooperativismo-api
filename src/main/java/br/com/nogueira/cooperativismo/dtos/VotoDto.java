package br.com.nogueira.cooperativismo.dtos;

import br.com.nogueira.cooperativismo.enums.VotoEnum;

import java.time.LocalDateTime;

public class VotoDto {

    private Long idAssociado;

    private Long idPauta;

    private VotoEnum voto;

    private LocalDateTime dataHoraVotacao;

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

    public void setDataHoraVotacao(LocalDateTime dataHoraVotacao) {
        this.dataHoraVotacao = dataHoraVotacao;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    @Override
    public String toString() {
        return "VotoDto{" +
                "idAssociado=" + idAssociado +
                ", idPauta=" + idPauta +
                ", voto=" + voto +
                ", dataHoraVotacao=" + dataHoraVotacao +
                '}';
    }
    
}
