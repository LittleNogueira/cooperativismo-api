package br.com.nogueira.cooperativismo.v1.forms;

import br.com.nogueira.cooperativismo.enums.VotoEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VotoForm {

    @NotNull
    private Long idAssociado;

    @NotNull
    private VotoEnum voto;

    private LocalDateTime dataHoraVotacao;

    public VotoForm(){
        this.dataHoraVotacao = LocalDateTime.now();
    }

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

    @Override
    public String toString() {
        return "VotoForm{" +
                "idAssociado=" + idAssociado +
                ", voto=" + voto +
                ", dataHoraVotacao=" + dataHoraVotacao +
                '}';
    }
}
