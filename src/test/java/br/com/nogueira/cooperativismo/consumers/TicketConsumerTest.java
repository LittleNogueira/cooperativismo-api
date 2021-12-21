package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.business.TicketBusiness;
import br.com.nogueira.cooperativismo.business.VotoBusiness;
import br.com.nogueira.cooperativismo.dtos.ResultadoDto;
import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.services.TicketService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketConsumerTest {

    @InjectMocks
    private TicketConsumer ticketConsumer;

    @Mock
    private TicketBusiness ticketBusiness;

    @Test
    public void testaConsumer(){
        ConsumerRecord<String, TicketDto> consumerRecord = new ConsumerRecord<String, TicketDto>("topico",1,1, UUID.randomUUID().toString(), new TicketDto());

        when(ticketBusiness.computaTicket(any())).thenReturn(new Ticket());

        ticketConsumer.consumer(consumerRecord);

        verify(ticketBusiness,times(1)).computaTicket(any(TicketDto.class));
    }

}
