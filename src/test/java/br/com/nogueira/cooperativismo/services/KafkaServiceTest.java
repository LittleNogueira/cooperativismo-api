package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.entities.Pauta;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KafkaServiceTest {

    @InjectMocks
    private KafkaService<Object> kafkaService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void testaEnvioDeMensagem(){
        when(kafkaTemplate.send(anyString(),any())).thenReturn(new SettableListenableFuture<>());

        ListenableFuture<SendResult<String, Object>> resultListenableFuture = kafkaService.send("topico",new Pauta());

        assertNotNull(resultListenableFuture);
        verify(kafkaTemplate,times(1)).send(anyString(),any());
    }
}
