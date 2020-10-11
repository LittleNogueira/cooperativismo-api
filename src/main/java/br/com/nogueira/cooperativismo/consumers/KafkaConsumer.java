package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Resultado;
import br.com.nogueira.cooperativismo.mappers.ResultdoMapper;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private PautaService pautaService;

    private static Logger Logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "topico")
    public void consumer(ConsumerRecord<String, ResultadoDto> consumerRecord){
        Logger.info("Inicia consumer de resultado {}", consumerRecord);

        Pauta pauta = pautaService.buscarPautaPorId(consumerRecord.value().getIdPauta());

        Logger.info("Inicia mapeamento do dto para entidade {}", consumerRecord.value());

        Resultado resultado = ResultdoMapper.INSTANCE.dtoParaEntidade(consumerRecord.value());

        Logger.info("Mapeamento realizado com sucesso {}", resultado);

        pauta.setResultado(resultado);

        Logger.info("Adiciona resultado para a pauta {}",pauta);

        pautaService.salvarPauta(pauta);

        Logger.info("Consumer finalizado com sucesso {}",pauta);
    }
}
