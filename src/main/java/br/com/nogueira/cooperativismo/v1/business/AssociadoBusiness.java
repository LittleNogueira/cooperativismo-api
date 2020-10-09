package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import br.com.nogueira.cooperativismo.v1.mappers.AssociadoMapper;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociadoBusiness {

    @Autowired
    private AssociadoService associadoService;

    public Associado criarAssociado(AssociadoForm associadoForm){
        Associado associado = AssociadoMapper.INSTANCE.fomularioParaEntidade(associadoForm);
        return associadoService.criarAssociado(associado);
    }

    public Associado buscarAssociadoPorId(Long id){
        return associadoService.buscaAssociadoPorId(id);
    }

}
