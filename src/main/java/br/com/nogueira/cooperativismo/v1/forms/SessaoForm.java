package br.com.nogueira.cooperativismo.v1.forms;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

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
