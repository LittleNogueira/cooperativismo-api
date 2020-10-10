package br.com.nogueira.cooperativismo.v1.forms;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SessaoForm {

    @Future
    private LocalDateTime dataHoraFinalizacao;

    public LocalDateTime getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }

    @Override
    public String toString() {
        return "SessaoForm{" +
                "dataHoraFinalizacao=" + dataHoraFinalizacao +
                '}';
    }
}
