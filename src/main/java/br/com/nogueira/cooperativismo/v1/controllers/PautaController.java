package br.com.nogueira.cooperativismo.v1.controllers;

import br.com.nogueira.cooperativismo.v1.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.mappers.SessaoMapper;
import br.com.nogueira.cooperativismo.v1.services.PautaService;
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
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaForm pautaForm, UriComponentsBuilder uriComponentsBuilder){
        Pauta pauta = pautaService.criarPauta(PautaMapper.INSTANCE.fomularioParaEntidade(pautaForm));
        URI uri = uriComponentsBuilder.path("/v1/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).body(pauta);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pauta> buscaPautaPorId(@PathVariable Long id){
        return ResponseEntity.ok(pautaService.buscaPautaPorId(id));
    }

    @PostMapping(path = "/{id}/sessoes")
    public ResponseEntity<Pauta> criarSessao(@PathVariable Long id, @Valid @RequestBody SessaoForm sessaoForm){
        return ResponseEntity.ok(pautaService.criarSessao(id,SessaoMapper.INSTANCE.fomularioParaEntidade(sessaoForm)));
    }

}
