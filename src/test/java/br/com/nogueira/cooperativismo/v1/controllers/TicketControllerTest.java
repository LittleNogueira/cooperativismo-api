package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.services.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @Test
    void testaBuscarTicketPorId(){
        when(ticketService.buscarTicketPorId(anyLong())).thenReturn(new Ticket());

        ResponseEntity<Ticket> associadoResponseEntity = ticketController.buscarPautaPorId(1l);

        assertNotNull(associadoResponseEntity.getBody());
        assertEquals(HttpStatus.OK,associadoResponseEntity.getStatusCode());
    }

}
