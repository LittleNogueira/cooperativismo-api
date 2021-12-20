package br.com.nogueira.cooperativismo.consumers;

import br.com.nogueira.cooperativismo.business.TicketBusiness;
import br.com.nogueira.cooperativismo.business.VotoBusiness;
import br.com.nogueira.cooperativismo.dtos.TicketDto;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.enums.StatusTicketEnum;
import br.com.nogueira.cooperativismo.services.TicketService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketConsumer {

    @Autowired
    private TicketBusiness ticketBusiness;

    private static Logger Logger = LoggerFactory.getLogger(TicketConsumer.class);

    @KafkaListener(topics = "${topic.ticket.name}")
    public void consumer(ConsumerRecord<String, TicketDto> consumerRecord){
        Logger.info("Inicia consumer de ticket {}", consumerRecord);

        TicketDto ticketDto = consumerRecord.value();

        Ticket ticket = ticketBusiness.computaTicket(ticketDto);

        Logger.info("Consumer finalizado com sucesso {}",ticket);
    }

}
