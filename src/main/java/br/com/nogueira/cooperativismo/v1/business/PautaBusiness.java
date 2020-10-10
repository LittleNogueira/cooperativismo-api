package br.com.nogueira.cooperativismo.v1.business;

import br.com.nogueira.cooperativismo.clients.UserClient;
import br.com.nogueira.cooperativismo.dtos.UserDto;
import br.com.nogueira.cooperativismo.enums.StatusEnum;
import br.com.nogueira.cooperativismo.exceptions.NotAcceptable;
import br.com.nogueira.cooperativismo.entities.Associado;
import br.com.nogueira.cooperativismo.entities.Pauta;
import br.com.nogueira.cooperativismo.entities.Sessao;
import br.com.nogueira.cooperativismo.entities.Voto;
import br.com.nogueira.cooperativismo.v1.controllers.PautaController;
import br.com.nogueira.cooperativismo.v1.forms.PautaForm;
import br.com.nogueira.cooperativismo.v1.forms.SessaoForm;
import br.com.nogueira.cooperativismo.v1.forms.VotoForm;
import br.com.nogueira.cooperativismo.v1.mappers.PautaMapper;
import br.com.nogueira.cooperativismo.v1.mappers.SessaoMapper;
import br.com.nogueira.cooperativismo.services.AssociadoService;
import br.com.nogueira.cooperativismo.services.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger Logger = LoggerFactory.getLogger(PautaBusiness.class);

    public Pauta criarPauta(PautaForm pautaForm){
        Logger.info("Formulario recebido na camanda de negócio {}", pautaForm);

        Logger.info("Inicia mapeamento do formulario para entidade {}",pautaForm);

        Pauta pauta = PautaMapper.INSTANCE.fomularioParaEntidade(pautaForm);

        Logger.info("Mapeamento realizado com sucesso {}",pauta);

        return pautaService.salvarPauta(pauta);
    }

    public Pauta criarSessao(Long idPauta, SessaoForm sessaoForm){
        Logger.info("Formulario recebido na camanda de negócio {}", sessaoForm);

        Pauta pauta = pautaService.buscaPautaPorId(idPauta);

        Logger.info("Inicia mapeamento do formulario para entidade {}", sessaoForm);

        Sessao sessao = SessaoMapper.INSTANCE.fomularioParaEntidade(sessaoForm);

        Logger.info("Mapeamento realizado com sucesso {}", sessao);

        if(Objects.nonNull(pauta.getSessao())){
            Logger.info("Está pauta já possui uma sessão {}",pauta);
            throw new NotAcceptable("Está pauta já possui uma sessão.");
        }

        if(Objects.isNull(sessao.getDataHoraFinalizacao())){
            LocalDateTime dataHora = sessao.getDataHoraCriacao().plusMinutes(1);

            Logger.info("Adiciona data e hora padrão para finalização da sessão {}",dataHora);

            sessao.setDataHoraFinalizacao(sessao.getDataHoraCriacao().plusMinutes(1));
        }

        pauta.setSessao(sessao);

        Logger.info("Adiciona sessão para a pauta {}",pauta);

        pauta = pautaService.salvarPauta(pauta);

        return pauta;
    }

    public Voto criarVoto(Long idPauta, VotoForm votoForm){
        Pauta pauta = pautaService.buscaPautaPorId(idPauta);
        validaSePautaEstaAptaParaVotacao(pauta,votoForm.getDataHoraVotacao());

        Associado associado = associadoService.buscaAssociadoPorId(votoForm.getIdAssociado());
        validaSeAssociadoEstaAptoParaVotarNaPauta(associado, pauta);

        Voto voto = new Voto(votoForm.getVoto(), associado, votoForm.getDataHoraVotacao());
        pauta.getSessao().getVotos().add(voto);

        pautaService.salvarPauta(pauta);

        return pauta.getSessao().getVotos().get(pauta.getSessao().getVotos().size() -1);
    }

    public Pauta buscarPautaPorId(Long id){
        Logger.info("Id {} recebido na camanda de negócio ", id);

        return pautaService.buscaPautaPorId(id);
    }

    private void validaSePautaEstaAptaParaVotacao(Pauta pauta, LocalDateTime dataHoraVotacao){
        if(Objects.isNull(pauta.getSessao())){
            throw new NotAcceptable("Esta pauta nao tem uma sessao aberta.");
        }

        if(dataHoraVotacao.isAfter(pauta.getSessao().getDataHoraFinalizacao())){
            throw new NotAcceptable("A sessao desta pauta ja foi fechada.");
        }
    }

    private void validaSeAssociadoEstaAptoParaVotarNaPauta(Associado associado, Pauta pauta){
        if(pautaService.existePautaComVotoDoAssociado(pauta.getId(), associado.getId())){
            throw new NotAcceptable("Um mesmo associado nao pode votar duas vezes.");
        }

        UserDto userDto = userClient.buscarUsuarioPorCpf(associado.getCpf());

        if(userDto.getStatus().equals(StatusEnum.UNABLE_TO_VOTE)){
            throw new NotAcceptable("O associado nao esta apto para votar.");
        }
    }

}
