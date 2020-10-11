package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.v1.forms.AssociadoForm;
import br.com.nogueira.cooperativismo.v1.mappers.AssociadoMapper;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociadoBusiness {

    @Autowired
    private AssociadoService associadoService;

    private static Logger Logger = LoggerFactory.getLogger(AssociadoBusiness.class);

    public Associado criarAssociado(AssociadoForm associadoForm){
        Logger.info("Formulario recebido na camanda de negócio {}", associadoForm);

        Logger.info("Inicia mapeamento do formulario para entidade {}", associadoForm);

        Associado associado = AssociadoMapper.INSTANCE.map(associadoForm);

        Logger.info("Mapeamento realizado com sucesso {}", associado);

        return associadoService.salvarAssociado(associado);
    }

    public Associado buscarAssociadoPorId(Long id){
        Logger.info("Id {} recebido na camanda de negócio ", id);

        return associadoService.buscaAssociadoPorId(id);
    }

}
