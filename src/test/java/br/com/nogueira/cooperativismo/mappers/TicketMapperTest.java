package br.com.nogueira.cooperativismo.mappers;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.dtos.VotoDto;
import br.com.nogueira.cooperativismo.entities.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketMapperTest {

    @Test
    public void testaMap(){
        VotoDto votoDto = new VotoDto();
        Ticket ticket = new Ticket();

        TicketDto ticketDto = TicketMapper.INSTANCE.map(votoDto,ticket);

        assertEquals(votoDto, ticketDto.getVotoDto());
        assertEquals(ticket.getStatus(), ticketDto.getStatus());
    }

}
