package br.com.nogueira.cooperativismo.v1.forms;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AssociadoForm {

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
