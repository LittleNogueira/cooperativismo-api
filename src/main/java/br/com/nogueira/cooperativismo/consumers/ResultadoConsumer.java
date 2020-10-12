package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.business.ResultadoBusiness;
import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResultadoConsumer {

    @Autowired
    private ResultadoBusiness resultadoBusiness;

    private static Logger Logger = LoggerFactory.getLogger(ResultadoConsumer.class);

    @KafkaListener(topics = "${topic.result.name}")
    public void consumer(ConsumerRecord<String, ResultadoDto> consumerRecord){
        Logger.info("Inicia consumer de resultado {}", consumerRecord);

        Pauta pauta = resultadoBusiness.criarResultado(consumerRecord.value());

        Logger.info("Consumer finalizado com sucesso {}",pauta);
    }

}
