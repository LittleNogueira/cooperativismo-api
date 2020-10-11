package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Resultado;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaConsumerTest {

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Mock
    private PautaService pautaService;

    @Test
    void testaConsumer(){
        ConsumerRecord<String, ResultadoDto> consumerRecord = new ConsumerRecord<String, ResultadoDto>("topico",1,1, UUID.randomUUID().toString(),new ResultadoDto(1l,1,1));

        when(pautaService.buscarPautaPorId(any())).thenReturn(new Pauta());

        kafkaConsumer.consumer(consumerRecord);

        verify(pautaService,times(1)).salvarPauta(any(Pauta.class));
        verify(pautaService,times(1)).buscarPautaPorId(anyLong());
    }

}
