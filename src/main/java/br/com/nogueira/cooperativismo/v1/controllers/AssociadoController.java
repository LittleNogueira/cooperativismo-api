package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.v1.business.AssociadoBusiness;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
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
@RequestMapping(path = "/v1/associados")
public class AssociadoController {

    @Autowired
    private AssociadoBusiness associadoBusiness;

    private static Logger Logger = LoggerFactory.getLogger(AssociadoController.class);

    @PostMapping
    @ApiOperation(value = "Cria um novo associado")
    public ResponseEntity<Associado> criarPauta(@Valid @RequestBody AssociadoForm associadoForm, UriComponentsBuilder uriComponentsBuilder){
        Logger.info("Requisição recebida para criar um novo associado {}", associadoForm);

        Associado associado = associadoBusiness.criarAssociado(associadoForm);

        Logger.info("Requisição concluida com sucesso {}", associado);

        URI uri = uriComponentsBuilder.path("/v1/associados/{id}").buildAndExpand(associado.getId()).toUri();
        return ResponseEntity.created(uri).body(associado);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca um associado por id")
    public ResponseEntity<Associado> buscaAssociadoPorId(@PathVariable Long id){
        Logger.info("Requisição recebida para buscar um associado com id {}", id);

        Associado associado = associadoBusiness.buscarAssociadoPorId(id);

        Logger.info("Requisição concluida com sucesso {}", associado);

        return ResponseEntity.ok(associado);
    }

}
