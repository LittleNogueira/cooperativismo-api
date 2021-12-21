package br.com.nogueira.cooperativismo.business;

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
import static org.mockito.Mockito.times;

@SpringBootTest
public class TicketBusinessTest {

    @InjectMocks
    private TicketBusiness ticketBusiness;

    @Mock
    private VotoBusiness votoBusiness;

    @Mock
    private TicketService ticketService;

    @Test
    public void testaConsumerComSucesso(){
        when(ticketService.buscarTicketPorId(any())).thenReturn(new Ticket());
        when(votoBusiness.criarVoto(any(TicketDto.class))).thenReturn(new Voto());

        ticketBusiness.computaTicket(new TicketDto());

        verify(ticketService,times(1)).salvarTicket(any(Ticket.class));
    }

    @Test
    public void testaConsumerComErro(){
        when(ticketService.buscarTicketPorId(any())).thenReturn(new Ticket());
        when(votoBusiness.criarVoto(any(TicketDto.class))).thenThrow(NotAcceptable.class);

        ticketBusiness.computaTicket(new TicketDto());

        verify(ticketService,times(1)).salvarTicket(any(Ticket.class));
    }
}
