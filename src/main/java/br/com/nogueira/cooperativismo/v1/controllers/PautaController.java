package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.CooperativismoApplication;
import br.com.nogueira.cooperativismo.entities.Ticket;
import br.com.nogueira.cooperativismo.v1.business.PautaBusiness;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(path = "/v1/pautas")
public class PautaController {

    @Autowired
    private PautaBusiness pautaBusiness;

    private static Logger Logger = LoggerFactory.getLogger(PautaController.class);

    @PostMapping
    @ApiOperation(value = "Cria uma nova pauta")
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaForm pautaForm, UriComponentsBuilder uriComponentsBuilder){
        Logger.info("Requisição recebida para criar uma nova pauta {}", pautaForm);

        Pauta pauta = pautaBusiness.criarPauta(pautaForm);

        Logger.info("Requisição concluida com sucesso {}", pauta);

        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(pauta);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca uma pauta por id")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id){
        Logger.info("Requisição recebida para buscar uma pauta com id {}", id);

        Pauta pauta  = pautaBusiness.buscarPautaPorId(id);

        Logger.info("Requisição concluida com sucesso {}", pauta);

        return ResponseEntity.ok(pauta);
    }

    @PostMapping(path = "/{id}/sessoes")
    @ApiOperation(value = "Cria uma novo sessão para a pauta")
    public ResponseEntity<Pauta> criarSessao(@PathVariable Long id, @Valid @RequestBody SessaoForm sessaoForm, UriComponentsBuilder uriComponentsBuilder){
        Logger.info("Requisição recebida para criar uma nova sessao {} para a pauta com id {}", sessaoForm, id);

        Pauta pauta = pautaBusiness.criarSessao(id,sessaoForm);

        Logger.info("Requisição concluida com sucesso {}", pauta);

        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(pauta);
    }

    @PostMapping(path = "/{id}/votos")
    @ApiOperation(value = "Cria um novo voto para uma pauta")
    public ResponseEntity<Ticket> criarVoto(@PathVariable Long id, @Valid @RequestBody VotoForm votoForm, UriComponentsBuilder uriComponentsBuilder){
        Logger.info("Requisição recebida para criar um voto {} para a pauta com id {}", votoForm, id);

        Ticket ticket = pautaBusiness.criarVoto(id,votoForm);

        Logger.info("Requisição concluida com sucesso {}", ticket);

        URI uri = uriComponentsBuilder.path("/v1/tickets/{id}").buildAndExpand(ticket.getId()).toUri();
        return ResponseEntity.created(uri).body(ticket);
    }

}
