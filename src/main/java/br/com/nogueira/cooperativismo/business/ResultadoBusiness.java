package br.com.nogueira.cooperativismo.business;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Resultado;
import br.com.nogueira.cooperativismo.mappers.ResultadoMapper;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultadoBusiness {

    @Autowired
    private PautaService pautaService;

    private static Logger Logger = LoggerFactory.getLogger(ResultadoBusiness.class);

    public Pauta criarResultado(ResultadoDto resultadoDto){
        Logger.info("Inicia consumer de resultado {}", resultadoDto);

        Pauta pauta = pautaService.buscarPautaPorId(resultadoDto.getIdPauta());

        Logger.info("Inicia mapeamento do dto para entidade {}", resultadoDto);

        Resultado resultado = ResultadoMapper.INSTANCE.dtoParaEntidade(resultadoDto);

        Logger.info("Mapeamento realizado com sucesso {}", resultado);

        pauta.setResultado(resultado);

        Logger.info("Adiciona resultado para a pauta {}",pauta);

        pautaService.salvarPauta(pauta);

        Logger.info("Consumer finalizado com sucesso {}",pauta);

        return pauta;
    }
}
