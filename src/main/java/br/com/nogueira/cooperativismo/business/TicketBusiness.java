package br.com.nogueira.cooperativismo.business;

import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.enums.StatusTicketEnum;
import br.com.nogueira.cooperativismo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketBusiness {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private VotoBusiness votoBusiness;

    public Ticket computaTicket(TicketDto ticketDto){
        Ticket ticket = ticketService.buscarTicketPorId(ticketDto.getId());

        try{
            votoBusiness.criarVoto(ticketDto);
            ticket.setStatus(StatusTicketEnum.FINALIZADO_COM_SUCESSO);
        }catch (Exception e){
            ticket.setMotivoErro(e.getMessage());
            ticket.setStatus(StatusTicketEnum.FINALIZADO_COM_ERRO);
        }

        return ticketService.salvarTicket(ticket);
    }
}
