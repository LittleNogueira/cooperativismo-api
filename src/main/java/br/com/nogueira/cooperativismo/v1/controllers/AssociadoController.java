package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.v1.business.AssociadoBusiness;
import br.com.nogueira.cooperativismo.v1.entities.Associado;
import br.com.nogueira.cooperativismo.v1.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.mappers.AssociadoMapper;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.services.AssociadoService;
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

    @PostMapping
    public ResponseEntity<Associado> criarPauta(@Valid @RequestBody AssociadoForm associadoForm, UriComponentsBuilder uriComponentsBuilder){
        Associado associado = associadoBusiness.criarAssociado(associadoForm);
        URI uri = uriComponentsBuilder.path("/v1/associados/{id}").buildAndExpand(associado.getId()).toUri();
        return ResponseEntity.created(uri).body(associado);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Associado> buscaAssociadoPorId(@PathVariable Long id){
        return ResponseEntity.ok(associadoBusiness.buscarAssociadoPorId(id));
    }

}
