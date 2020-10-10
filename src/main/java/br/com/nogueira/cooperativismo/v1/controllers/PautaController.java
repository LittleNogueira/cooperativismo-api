package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.v1.business.PautaBusiness;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping
    @ApiOperation(value = "Cria uma nova pauta")
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaForm pautaForm, UriComponentsBuilder uriComponentsBuilder){
        Pauta pauta = pautaBusiness.criarPauta(pautaForm);
        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(pauta);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca uma pauta por id")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id){
        return ResponseEntity.ok(pautaBusiness.buscarPautaPorId(id));
    }

    @PostMapping(path = "/{id}/sessoes")
    @ApiOperation(value = "Cria uma novo sessão para a pauta")
    public ResponseEntity<Pauta> criarSessao(@PathVariable Long id, @Valid @RequestBody SessaoForm sessaoForm, UriComponentsBuilder uriComponentsBuilder){
        Pauta pauta = pautaBusiness.criarSessao(id,sessaoForm);
        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(pauta);
    }

    @PostMapping(path = "/{id}/votos")
    @ApiOperation(value = "Cria um novo voto para uma pauta")
    public ResponseEntity<Voto> criarVoto(@PathVariable Long id, @Valid @RequestBody VotoForm votoForm, UriComponentsBuilder uriComponentsBuilder){
        Voto voto = pautaBusiness.criarVoto(id,votoForm);
        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(voto);
    }

}
