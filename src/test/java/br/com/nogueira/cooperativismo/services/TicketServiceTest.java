package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void testaCriarTicketComSucesso(){
        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());

        Ticket ticket = ticketService.salvarTicket(new Ticket());

        assertNotNull(ticket);
        verify(ticketRepository,times(1)).save(any(Ticket.class));
    }

    @Test
    public void testaBuscaAssociadoPorIdComIdQueNaoExiste() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            ticketService.buscarTicketPorId(1l);
        });

        assertEquals("Ticket com id 1 não existe.", exception.getMessage());
    }

    @Test
    public void testaBuscaAssociadoPorIdComSucesso() {
        when(ticketRepository.findById(1l)).thenReturn(Optional.of(new Ticket()));

        Ticket ticket = ticketService.buscarTicketPorId(1l);

        assertNotNull(ticket);
    }

}
