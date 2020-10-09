package br.com.nogueira.cooperativismo.dtos;

import br.com.nogueira.cooperativismo.enums.ResultadoEnum;

public class ResultadoDto {

    private Long idPauta;

    private int votosSim;

    private int votosNao;

    private ResultadoEnum resultado;

    public ResultadoDto(){}

    public ResultadoDto(Long idPauta,int votosSim, int votosNao){
        this.idPauta = idPauta;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
        this.resultado = ResultadoEnum.EMPATADO;

        if(votosSim > votosNao){
            this.resultado = ResultadoEnum.APROVADO;
        }else if(votosNao > votosSim){
            this.resultado = ResultadoEnum.REPROVADO;
        }
    }

    public Long getIdPauta(){
        return this.idPauta;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }
}
