package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.business.ResultadoBusiness;
import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ResultadoConsumerTest {

    @InjectMocks
    private ResultadoConsumer resultadoConsumer;

    @Mock
    private ResultadoBusiness resultadoBusiness;

    @Test
    public void testaConsumer(){
        ConsumerRecord<String, ResultadoDto> consumerRecord = new ConsumerRecord<String, ResultadoDto>("topico",1,1, UUID.randomUUID().toString(),new ResultadoDto(1l,1,1));

        when(resultadoBusiness.criarResultado(any())).thenReturn(new Pauta());

        resultadoConsumer.consumer(consumerRecord);

        verify(resultadoBusiness,times(1)).criarResultado(any(ResultadoDto.class));
    }

}
