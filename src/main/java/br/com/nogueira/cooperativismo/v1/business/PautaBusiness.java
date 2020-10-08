package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.v1.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.entities.Sessao;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.mappers.SessaoMapper;
import br.com.nogueira.cooperativismo.v1.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PautaBusiness {

    @Autowired
    private PautaService pautaService;

    public Pauta criarPauta(PautaForm pautaForm){
        Pauta pauta = PautaMapper.INSTANCE.fomularioParaEntidade(pautaForm);
        return pautaService.criarPauta(pauta);
    }

    public Pauta criarSessao(Long id, SessaoForm sessaoForm){
        Pauta pauta = pautaService.buscaPautaPorId(id);
        Sessao sessao = SessaoMapper.INSTANCE.fomularioParaEntidade(sessaoForm);

        if(Objects.nonNull(pauta.getSessao())){
            throw new NotAcceptable("Esta pauta ja tem uma sessao.");
        }

        if(Objects.isNull(sessao.getDataHoraFinalizacao())){
            sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusMinutes(1));
        }

        pauta.setSessao(sessao);

        return pautaService.criarPauta(pauta);
    }

    public Pauta buscarPautaPorId(Long id){
        return pautaService.buscaPautaPorId(id);
    }

}
