package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.business.VotoBusiness;
import br.com.nogueira.cooperativismo.dtos.TicketDto;
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
    private VotoBusiness votoBusiness;

    @Mock
    private TicketService ticketService;

    @Test
    public void testaConsumerComSucesso(){
        ConsumerRecord<String, TicketDto> consumerRecord = new ConsumerRecord<String, TicketDto>("topico",1,1, UUID.randomUUID().toString(),new TicketDto());

        when(ticketService.buscarTicketPorId(any())).thenReturn(new Ticket());
        when(votoBusiness.criarVoto(any(TicketDto.class))).thenReturn(new Voto());

        ticketConsumer.consumer(consumerRecord);

        verify(ticketService,times(1)).salvarTicket(any(Ticket.class));
    }

    @Test
    public void testaConsumerComErro(){
        ConsumerRecord<String, TicketDto> consumerRecord = new ConsumerRecord<String, TicketDto>("topico",1,1, UUID.randomUUID().toString(),new TicketDto());

        when(ticketService.buscarTicketPorId(any())).thenReturn(new Ticket());
        when(votoBusiness.criarVoto(any(TicketDto.class))).thenThrow(NotAcceptable.class);

        ticketConsumer.consumer(consumerRecord);

        verify(ticketService,times(1)).salvarTicket(any(Ticket.class));
    }

}
