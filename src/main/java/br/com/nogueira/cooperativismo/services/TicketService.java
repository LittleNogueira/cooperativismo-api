package br.com.nogueira.cooperativismo.services;

import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.repository.TicketRepository;
import com.google.common.base.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private static Logger Logger = LoggerFactory.getLogger(TicketService.class);

    public Ticket salvarTicker(Ticket ticket){
        Logger.info("Entidade recebida na camanda de serviço {}", ticket);

        ticketRepository.save(ticket);

        Logger.info("Entidade persistida com sucesso {}", ticket);

        return ticket;
    }

    public Ticket buscarTicketPorId(Long id){
        Logger.info("Id {} recebido na camanda de serviço para realizar busca", id);

        Optional<Ticket> ticket = ticketRepository.findById(id);

        Logger.info("Busca realizada com sucesso {}", ticket);

        if(ticket.isEmpty()){
            Logger.info("Não existe ticket com id {}", id);
            throw new NotFoundException(MessageFormat.format("Ticket com id {0} não existe.",id));
        }

        Logger.info("Ticket encontrada com sucesso {}", ticket.get());
        return ticket.get();
    }

}
