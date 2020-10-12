package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.services.TicketService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    private static Logger Logger = LoggerFactory.getLogger(TicketController.class);

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca uma pauta por id")
    public ResponseEntity<Ticket> buscarPautaPorId(@PathVariable Long id){
        Logger.info("Requisição recebida para buscar um ticket com id {}", id);

        Ticket ticket  = ticketService.buscarTicketPorId(id);

        Logger.info("Requisição concluida com sucesso {}", ticket);

        return ResponseEntity.ok(ticket);
    }

}
