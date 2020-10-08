package br.com.nogueira.cooperativismo.v1.forms;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PautaForm {

    @NotNull
    @Size(min = 5)
    private String titulo;

    @NotNull
    @Size(max = 255, min = 20)
    private String descricao;

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
