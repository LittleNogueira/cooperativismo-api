package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.clients.UserClient;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.exceptions.NotFoundException;
import br.com.nogueira.cooperativismo.v1.entities.Associado;
import br.com.nogueira.cooperativismo.v1.entities.Pauta;
import br.com.nogueira.cooperativismo.v1.entities.Sessao;
import br.com.nogueira.cooperativismo.v1.entities.Voto;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.mappers.SessaoMapper;
import br.com.nogueira.cooperativismo.v1.services.AssociadoService;
import br.com.nogueira.cooperativismo.v1.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class PautaBusiness {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private UserClient userClient;

    public Pauta criarPauta(PautaForm pautaForm){
        Pauta pauta = PautaMapper.INSTANCE.fomularioParaEntidade(pautaForm);
        return pautaService.salvarPauta(pauta);
    }

    public Pauta criarSessao(Long idPauta, SessaoForm sessaoForm){
        Pauta pauta = pautaService.buscaPautaPorId(idPauta);
        Sessao sessao = SessaoMapper.INSTANCE.fomularioParaEntidade(sessaoForm);

        if(Objects.nonNull(pauta.getSessao())){
            throw new NotAcceptable("Esta pauta ja tem uma sessao.");
        }

        if(Objects.isNull(sessao.getDataHoraFinalizacao())){
            sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusMinutes(1));
        }

        pauta.setSessao(sessao);

        return pautaService.salvarPauta(pauta);
    }

    public Voto criarVoto(Long idPauta, VotoForm votoForm){
        Pauta pauta = pautaService.buscaPautaPorId(idPauta);
        validaSePautaEstaAptaParaVotacao(pauta,votoForm.getDataHoraVotacao());

        Associado associado = associadoService.buscaAssociadoPorId(votoForm.getIdAssociado());
        validaSeAssociadoEstaAptoParaVotarNaPauta(associado, pauta);

        Voto voto = new Voto(votoForm.getVoto(), associado, votoForm.getDataHoraVotacao());
        pauta.getSessao().getVotos().add(voto);

        pautaService.salvarPauta(pauta);

        return voto;
    }

    public Pauta buscarPautaPorId(Long id){
        return pautaService.buscaPautaPorId(id);
    }

    private void validaSePautaEstaAptaParaVotacao(Pauta pauta, LocalDateTime dataHoraVotacao){
        if(Objects.isNull(pauta.getSessao())){
            throw new NotAcceptable("Esta pauta nao tem uma sessao aberta.");
        }

        if(dataHoraVotacao.isAfter(pauta.getSessao().getDataHoraCriacao())){
            throw new NotAcceptable("A sessao desta pauta ja foi fechada.");
        }
    }

    private void validaSeAssociadoEstaAptoParaVotarNaPauta(Associado associado, Pauta pauta){
        if(pautaService.existePautaComVotoDoAssociado(pauta.getId(), associado.getId())){
            throw new NotAcceptable("Um mesmo associado nao pode votar duas vezes.");
        }

        UserDto userDto = userClient.buscarUsuarioPorCpf(associado.getCpf());

        if(userDto.getStatusEnum().equals(StatusEnum.UNABLE_TO_VOTE)){
            throw new NotAcceptable("O associado nao esta apto para votar.");
        }
    }

}
