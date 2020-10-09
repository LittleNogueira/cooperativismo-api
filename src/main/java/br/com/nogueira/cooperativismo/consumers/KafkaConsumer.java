package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Resultado;
import br.com.nogueira.cooperativismo.mappers.ResultdoMapper;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private PautaService pautaService;

    @KafkaListener(topics = "topico")
    public void consume(ConsumerRecord<String, ResultadoDto> consumerRecord){
        Pauta pauta = pautaService.buscaPautaPorId(consumerRecord.value().getIdPauta());
        Resultado resultado = ResultdoMapper.INSTANCE.dtoParaEntidade(consumerRecord.value());

        pauta.setResultado(resultado);

        pautaService.salvarPauta(pauta);
    }
}
